package io.github.renepanke.restimaps.repository;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.lang.Strings;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.activation.DataSource;
import jakarta.inject.Singleton;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Bean
@Singleton
public class AttachmentRepository {

    public Map<String, DataSource> getAttachments(Message message) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException {
        if (message == null) {
            return Map.of();
        }
        return getAttachmentsInternal(message);
    }

    public Optional<DataSource> getAttachment(String attachmentId, Message message) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException {
        return this.getAttachments(message).entrySet().stream().filter(entry -> Objects.equals(attachmentId, entry.getKey())).map(Map.Entry::getValue).findFirst();
    }

    public StreamedFile getAttachmentContent(String attachmentId, Message message) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException, AttachmentNotFoundException, IOException {
        DataSource dataSource = getAttachment(attachmentId, message).orElseThrow(AttachmentNotFoundException::new);
        String contentType = Objects.requireNonNullElse(dataSource.getContentType(), "application/octet-stream");
        return new StreamedFile(dataSource.getInputStream(), new MediaType(contentType));
    }

    private static Map<String, DataSource> getAttachmentsInternal(Message message) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException {
        MimePart part = (MimeMessage) message;
        return getAttachmentsRecursive(part, new HashMap<>());
    }

    private static Map<String, DataSource> getAttachmentsRecursive(MimePart part, Map<String, DataSource> attachments) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentDataSourceNotRetrievableException, AttachmentMediaTypeNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentContentIdNotRetrievableException {
        Map<String, DataSource> recursiveAttachments = new HashMap<>(attachments);
        if (isMediaType(part)) {
            Multipart multipart;
            try {
                multipart = (Multipart) part.getContent();
            } catch (IOException | MessagingException e) {
                throw new AttachmentDataSourceNotRetrievableException(e);
            }
            int mpCount;
            try {
                mpCount = multipart.getCount();
            } catch (MessagingException e) {
                throw new AttachmentPartsNotRetrievableException(e);
            }
            for (int mpIdx = 0; mpIdx < mpCount; mpIdx++) {
                Map<String, DataSource> childAttachments = new HashMap<>();
                try {
                    childAttachments = getAttachmentsRecursive((MimeBodyPart) multipart.getBodyPart(mpIdx), attachments);
                } catch (MessagingException e) {
                    // TODO: Log
                }
                recursiveAttachments.putAll(childAttachments);
            }
        } else {
            String contentId;
            try {
                contentId = stripContentId(part.getContentID());
            } catch (MessagingException e) {
                throw new AttachmentContentIdNotRetrievableException(e);
            }
            DataSource dataSource = createDataSource(part);
            if (contentId != null) {
                recursiveAttachments.put(contentId, dataSource);
            }
            // Possibly contentId is nullable here and then it's being ignored.
        }
        return recursiveAttachments;
    }


    private static boolean isMediaType(MimePart part) throws AttachmentMediaTypeNotRetrievableException {
        try {
            return new ContentType(part.getDataHandler().getContentType()).match("multipart/*");
        } catch (MessagingException e) {
            throw new AttachmentMediaTypeNotRetrievableException(e);
        }
    }

    private static String stripContentId(String contentId) {
        return contentId == null ? null : contentId.trim().replaceAll("[<>]", "");
    }

    private static DataSource createDataSource(MimePart part) throws AttachmentDataSourceNotRetrievableException, AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable {
        DataSource dataSource = null;
        try {
            dataSource = part.getDataHandler().getDataSource();
        } catch (MessagingException e) {
            throw new AttachmentDataSourceNotRetrievableException(e);
        }
        String contentType = getBaseMimeType(dataSource.getContentType());
        String dataSourceName = getDataSourceName(part, dataSource);
        try {
            return new InputStreamDataSource(dataSource.getInputStream(), contentType, dataSourceName);
        } catch (IOException e) {
            throw new AttachmentDataSourceInputStreamNotRetrievable(e);
        }


    }

    private static String getBaseMimeType(String contentType) {
        int separatorIndex = contentType.indexOf(';');
        return separatorIndex < 0 ? contentType : contentType.substring(0, separatorIndex);
    }

    private static String getDataSourceName(MimePart part, DataSource dataSource) throws AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException {
        String result = dataSource.getName();
        if (Strings.isBlank(result)) {
            try {
                result = part.getFileName();
            } catch (MessagingException e) {
                throw new AttachmentNameNotRetrievableException(e);
            }
        }
        if (Strings.isNotBlank(result)) {
            try {
                result = MimeUtility.decodeText(result);
            } catch (UnsupportedEncodingException e) {
                throw new UnsupportedAttachmentEncodingException(e);
            }
        } else {
            result = null;
        }
        return result;
    }
}
