package io.github.renepanke.restimaps.controller;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.model.Attachment;
import io.github.renepanke.restimaps.model.Email;
import io.github.renepanke.restimaps.model.Folder;
import io.github.renepanke.restimaps.service.AttachmentService;
import io.github.renepanke.restimaps.service.EmailService;
import io.github.renepanke.restimaps.service.FolderService;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import org.zalando.problem.Problem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Bean
@Controller("/restimap/folders")
@ExecuteOn(TaskExecutors.BLOCKING)
public class RestImapController {

    private final FolderService folderService;
    private final EmailService emailService;
    private final AttachmentService attachmentService;

    @Inject
    public RestImapController(FolderService folderService, EmailService emailService, AttachmentService attachmentService) {
        this.folderService = folderService;
        this.emailService = emailService;
        this.attachmentService = attachmentService;
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<List<Folder>> getFolders(HttpRequest<?> request) {
        String jwt = request.getAttribute("jwt").map(Object::toString).get();
        try {
            return HttpResponse.ok(this.folderService.getFolders());
        } catch (FoldersNotRetrievableException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Folder> getFolder(@PathVariable @NotBlank String folderId) {
        try {
            return HttpResponse.ok(this.folderService.getFolder(folderId));
        } catch (FolderNotFoundException | EmailsNotRetrievableException | FolderNotOpenableException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<List<Email>> getEmails(@PathVariable @NotBlank String folderId) {
        try {
            return HttpResponse.ok(this.emailService.getEmails(folderId));
        } catch (FolderNotFoundException | EmailsNotRetrievableException | FolderNotOpenableException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails/{emailId}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Email> getEmail(@PathVariable @NotBlank String folderId, @PathVariable @NotBlank String emailId) {
        try {
            return HttpResponse.ok(this.emailService.getEmail(folderId, emailId));
        } catch (EmailRecipientsNotRetrievable | FolderNotFoundException | EmailsNotRetrievableException |
                 FolderNotOpenableException | EmailDateNotRetrievableException |
                 EmailReadReceiptStatusNotRetrievableException | EmailFromNotRetrievable |
                 EmailReplyToNotRetrievableException | AttachmentDataSourceNotRetrievableException |
                 AttachmentNameNotRetrievableException | UnsupportedAttachmentEncodingException |
                 AttachmentDataSourceInputStreamNotRetrievable | AttachmentMediaTypeNotRetrievableException |
                 AttachmentContentIdNotRetrievableException | EmailReadStatusNotRetrievableException |
                 EmailSubjectNotRetrievableException | AttachmentPartsNotRetrievableException |
                 EmailContentNotRetrievableException | EmailSenderNotRetrievableException | EmailIdNotFoundException |
                 EmailIdNotRetrievableException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails/{emailId}/content")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<StreamedFile> getEmailContent(@PathVariable @NotBlank String folderId, @PathVariable @NotBlank String emailId) {
        try {
            return HttpResponse.ok(this.emailService.getEmailContent(folderId, emailId));
        } catch (FolderNotFoundException | EmailsNotRetrievableException | FolderNotOpenableException | IOException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails/{emailId}/attachments")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<List<Attachment>> getAttachments(@PathVariable @NotBlank String folderId, @PathVariable @NotBlank String emailId) {
        try {
            return HttpResponse.ok(this.attachmentService.getAttachments(folderId, emailId));
        } catch (FolderNotFoundException | EmailsNotRetrievableException | FolderNotOpenableException |
                 EmailNotFoundException | AttachmentNameNotRetrievableException |
                 UnsupportedAttachmentEncodingException | AttachmentDataSourceInputStreamNotRetrievable |
                 AttachmentMediaTypeNotRetrievableException | AttachmentContentIdNotRetrievableException |
                 AttachmentPartsNotRetrievableException | AttachmentDataSourceNotRetrievableException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails/{emailId}/attachments/{attachmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Attachment> getAttachment(@PathVariable @NotBlank String folderId, @PathVariable @NotBlank String emailId, @PathVariable @NotBlank String attachmentId) {
        try {
            return HttpResponse.ok(this.attachmentService.getAttachment(folderId, emailId, attachmentId));
        } catch (FolderNotFoundException | EmailNotFoundException | AttachmentNameNotRetrievableException |
                 EmailsNotRetrievableException | UnsupportedAttachmentEncodingException |
                 AttachmentDataSourceInputStreamNotRetrievable | AttachmentMediaTypeNotRetrievableException |
                 AttachmentContentIdNotRetrievableException | AttachmentPartsNotRetrievableException |
                 AttachmentDataSourceNotRetrievableException | FolderNotOpenableException |
                 AttachmentNotFoundException e) {
            throw Problem.builder().build();
        }
    }

    @Get("/{folderId}/emails/{emailId}/attachments/{attachmentId}/content")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<StreamedFile> getAttachmentContent(@PathVariable @NotBlank String folderId, @PathVariable @NotBlank String emailId, @PathVariable @NotBlank String attachmentId) {
        try {
            return HttpResponse.ok(this.attachmentService.getAttachmentContent(folderId, emailId, attachmentId));
        } catch (FolderNotFoundException | EmailsNotRetrievableException | FolderNotOpenableException |
                 EmailNotFoundException | AttachmentNameNotRetrievableException |
                 UnsupportedAttachmentEncodingException | AttachmentDataSourceInputStreamNotRetrievable |
                 AttachmentMediaTypeNotRetrievableException | AttachmentContentIdNotRetrievableException |
                 AttachmentNotFoundException | AttachmentPartsNotRetrievableException | IOException |
                 AttachmentDataSourceNotRetrievableException e) {
            throw Problem.builder().build();
        }
    }
}
