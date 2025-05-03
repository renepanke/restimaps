package io.github.renepanke.restimaps.service;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.mapper.AttachmentMapper;
import io.github.renepanke.restimaps.model.Attachment;
import io.github.renepanke.restimaps.repository.AttachmentRepository;
import io.github.renepanke.restimaps.repository.EmailRepository;
import io.github.renepanke.restimaps.repository.FolderRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.activation.DataSource;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Folder;
import jakarta.mail.Message;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Bean
@Singleton
public class AttachmentService {

    private final EmailRepository emailRepository;
    private final FolderRepository folderRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Inject
    public AttachmentService(EmailRepository emailRepository, FolderRepository folderRepository, AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.emailRepository = emailRepository;
        this.folderRepository = folderRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    public List<Attachment> getAttachments(String folderId, String emailId, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException, EmailNotFoundException, AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException {
        Folder folder = this.folderRepository.getFolder(folderId, jwt);
        Message message = this.emailRepository.getEmail(folder, emailId).orElseThrow(EmailNotFoundException::new);
        Map<String, DataSource> attachments = this.attachmentRepository.getAttachments(message);
        return attachments.entrySet().stream()
                .map(it -> {
                    try {
                        return this.attachmentMapper.map(it.getKey(), it.getValue());
                    } catch (AttachmentDataSourceInputStreamNotRetrievable e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public Attachment getAttachment(String folderId, String emailId, String attachmentId, String jwt) throws FolderNotFoundException, EmailNotFoundException, AttachmentNameNotRetrievableException, EmailsNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentPartsNotRetrievableException, AttachmentDataSourceNotRetrievableException, FolderNotOpenableException, AttachmentNotFoundException {
        return this.getAttachments(folderId, emailId, jwt)
                .stream().filter(it -> Objects.equals(attachmentId, it.getId()))
                .findFirst()
                .orElseThrow(AttachmentNotFoundException::new);
    }

    public StreamedFile getAttachmentContent(String folderId, String emailId, String attachmentId, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException, EmailNotFoundException, AttachmentNameNotRetrievableException, UnsupportedAttachmentEncodingException, AttachmentDataSourceInputStreamNotRetrievable, AttachmentMediaTypeNotRetrievableException, AttachmentContentIdNotRetrievableException, AttachmentNotFoundException, AttachmentPartsNotRetrievableException, IOException, AttachmentDataSourceNotRetrievableException {
        Folder folder = this.folderRepository.getFolder(folderId, jwt);
        Message message = this.emailRepository.getEmail(folder, emailId).orElseThrow(EmailNotFoundException::new);
        return this.attachmentRepository.getAttachmentContent(attachmentId, message);
    }
}
