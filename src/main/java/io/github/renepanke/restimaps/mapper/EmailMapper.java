package io.github.renepanke.restimaps.mapper;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.lang.Tuple;
import io.github.renepanke.restimaps.model.Email;
import io.github.renepanke.restimaps.repository.AttachmentRepository;
import io.github.renepanke.restimaps.repository.EmailRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.MediaType;
import jakarta.activation.DataSource;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Bean
@Singleton
public class EmailMapper {

    private final AttachmentRepository attachmentRepository;

    @Inject
    public EmailMapper(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Email map(Message message) throws EmailRecipientsNotRetrievable, EmailFromNotRetrievable, EmailDateNotRetrievableException, EmailSubjectNotRetrievableException, EmailSenderNotRetrievableException, EmailReadStatusNotRetrievableException, EmailReplyToNotRetrievableException, EmailReadReceiptStatusNotRetrievableException, EmailContentNotRetrievableException, AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException, EmailIdNotFoundException, EmailIdNotRetrievableException {
        Email email = new Email();
        email.setInternetMessagId(EmailRepository.getInternetMessageId(message));
        email.setBcc(this.getRecipients(message, Message.RecipientType.BCC));
        email.setCc(this.getRecipients(message, Message.RecipientType.CC));
        email.setTo(this.getRecipients(message, Message.RecipientType.TO));
        try {
            String from = Arrays.stream(message.getFrom()).map(InternetAddress.class::cast)
                    .map(InternetAddress::getAddress)
                    .findFirst()
                    .orElseThrow(EmailFromNotRetrievable::new);
            email.setFrom(from);
        } catch (MessagingException e) {
            throw new EmailFromNotRetrievable(e);
        }
        try {
            String sendingAndCreationTimeStamp = this.toIso8601Utc(message.getSentDate());
            email.setCreationTimestamp(sendingAndCreationTimeStamp);
            email.setSentTimestamp(sendingAndCreationTimeStamp);
            String receivedTimeStamp = this.toIso8601Utc(message.getReceivedDate());
            email.setReceivedTimestamp(receivedTimeStamp);
            if (message.getSentDate().after(message.getReceivedDate())) {
                email.setLastModifiedTimestamp(sendingAndCreationTimeStamp);
            } else {
                email.setLastModifiedTimestamp(receivedTimeStamp);
            }
        } catch (MessagingException e) {
            throw new EmailDateNotRetrievableException(e);
        }
        email.setFolderId(message.getFolder().getName());
        try {
            email.setSubject(message.getSubject());
        } catch (MessagingException e) {
            throw new EmailSubjectNotRetrievableException(e);
        }
        try {
            String sender = ((InternetAddress) ((MimeMessage) message).getSender()).getAddress();
            email.setSender(sender);
        } catch (MessagingException e) {
            throw new EmailSenderNotRetrievableException(e);
        }
        try {
            email.setRead(message.getFlags().contains(Flags.Flag.SEEN));
        } catch (MessagingException e) {
            throw new EmailReadStatusNotRetrievableException(e);
        }
        try {
            List<String> replyTo = Arrays.stream(message.getReplyTo()).map(InternetAddress.class::cast)
                    .map(InternetAddress::getAddress).toList();
            email.setReplyTo(replyTo);
        } catch (MessagingException e) {
            throw new EmailReplyToNotRetrievableException(e);
        }
        email.setReadReceiptRequested(this.getReadReceiptRequested(message));
        Tuple<String, String> plainAndHtmlContent = extractContent(message);
        email.setPlainContent(plainAndHtmlContent.getLeft());
        email.setHtmlContent(plainAndHtmlContent.getRight());
        Map<String, DataSource> attachmentsMap = this.attachmentRepository.getAttachments(message);
        email.setHasAttachments(!attachmentsMap.isEmpty());
        email.setAttachmentIds(attachmentsMap.keySet().stream().toList());
        return email;
    }

    private List<String> getRecipients(Message message, Message.RecipientType recipientType) throws EmailRecipientsNotRetrievable {
        try {
            Address[] recipients = message.getRecipients(recipientType);
            if (recipients == null) {
                return List.of();
            }
            return Arrays.stream(recipients)
                    .map(InternetAddress.class::cast)
                    .map(InternetAddress::getAddress)
                    .toList();
        } catch (MessagingException e) {
            throw new EmailRecipientsNotRetrievable(e);
        }
    }

    private String toIso8601Utc(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toInstant)
                .map(it -> it.atZone(ZoneId.of("UTC")))
                .map(it -> it.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .orElse(null);
    }

    private boolean getReadReceiptRequested(Message message) throws EmailReadReceiptStatusNotRetrievableException {
        String[] values;
        try {
            values = message.getHeader("Disposition-Notification-To");
        } catch (MessagingException e) {
            throw new EmailReadReceiptStatusNotRetrievableException(e);
        }
        if (values != null && values.length > 0) {
            return true;
        }
        return false;
    }

    private Tuple<String, String> extractContent(Part p) throws EmailContentNotRetrievableException {
        String plainText = null;
        String htmlText = null;
        try {
            if (p.isMimeType(MediaType.TEXT_PLAIN)) {
                plainText = (String) p.getContent();
            } else if (p.isMimeType(MediaType.TEXT_HTML)) {
                htmlText = (String) p.getContent();
            } else if (p.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) p.getContent();
                for (int mpIdx = 0; mpIdx < mp.getCount(); mpIdx++) {
                    Part bp = mp.getBodyPart(mpIdx);
                    Tuple<String, String> content = extractContent(bp);
                    if (content.getLeft() != null) {
                        plainText = content.getLeft();
                    }
                    if (content.getRight() != null) {
                        htmlText = content.getRight();
                    }
                }
            }
        } catch (MessagingException | IOException e) {
            throw new EmailContentNotRetrievableException(e);
        }
        return Tuple.of(plainText, htmlText);
    }
}
