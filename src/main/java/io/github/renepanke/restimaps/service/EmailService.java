package io.github.renepanke.restimaps.service;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.mapper.EmailMapper;
import io.github.renepanke.restimaps.model.Email;
import io.github.renepanke.restimaps.repository.EmailRepository;
import io.github.renepanke.restimaps.repository.FolderRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Objects;

@Bean
@Singleton
public class EmailService {

    private final EmailRepository emailRepository;
    private final FolderRepository folderRepository;
    private final EmailMapper emailMapper;

    @Inject
    public EmailService(EmailRepository emailRepository, FolderRepository folderRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.folderRepository = folderRepository;
        this.emailMapper = emailMapper;
    }

    public List<Email> getEmails(String folderId, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException {
        Folder folder = this.folderRepository.getFolder(folderId, jwt);
        return this.emailRepository.getEmails(folder).stream()
                .map(it -> {
                    try {
                        return this.emailMapper.map(it);
                    } catch (RestImapException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public Email getEmail(String folderId, String emailId, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException, EmailRecipientsNotRetrievable, EmailDateNotRetrievableException, EmailReadReceiptStatusNotRetrievableException, EmailFromNotRetrievable, EmailReplyToNotRetrievableException, AttachmentDataSourceNotRetrievableException, AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, EmailReadStatusNotRetrievableException, EmailSubjectNotRetrievableException, AttachmentPartsNotRetrievableException, EmailContentNotRetrievableException, EmailSenderNotRetrievableException, EmailIdNotFoundException, EmailIdNotRetrievableException {
        Folder folder = this.folderRepository.getFolder(folderId, jwt);
        Message message = this.emailRepository.getEmail(folder, emailId).orElse(null);
        if (message == null) {
            return null;
        }
        return this.emailMapper.map(message);
    }

    public StreamedFile getEmailContent(String folderId, String emailId, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException, IOException {
        Folder folder = this.folderRepository.getFolder(folderId, jwt);
        Message message = this.emailRepository.getEmail(folder, emailId).orElse(null);
        if (message == null) {
            return null;
        }
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        Thread writer = new Thread(() -> {
            try {
                message.writeTo(out);
                out.close();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.start();
        return new StreamedFile(in, new MediaType("message/rfc822"));
    }
}
