package io.github.renepanke.restimaps.controller

import io.github.renepanke.restimaps.model.AttachmentK
import io.github.renepanke.restimaps.model.EmailK
import io.github.renepanke.restimaps.model.FolderK
import io.github.renepanke.restimaps.service.AttachmentServiceK
import io.github.renepanke.restimaps.service.EmailServiceK
import io.github.renepanke.restimaps.service.FolderServiceK
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.server.types.files.StreamedFile
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject
import jakarta.validation.constraints.NotBlank

@Controller("/restimaps/folders")
@ExecuteOn(TaskExecutors.BLOCKING)
open class RestImapsControllerK {

    private val attachmentService: AttachmentServiceK
    private val emailService: EmailServiceK
    private val folderService: FolderServiceK

    @Inject
    constructor(attachmentService: AttachmentServiceK, emailService: EmailServiceK, folderService: FolderServiceK) {
        this.attachmentService = attachmentService
        this.emailService = emailService
        this.folderService = folderService
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    open fun getFolders(request: HttpRequest<*>): HttpResponse<List<FolderK>> {
        return HttpResponse.ok(folderService.getFolders(jwt(request)))
    }

    @Get("/{folderId}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getFolder(request: HttpRequest<*>, @PathVariable @NotBlank folderId: String): HttpResponse<FolderK> {
        return HttpResponse.ok(folderService.getFolder(folderId, jwt(request)))
    }

    @Get("/{folderId}/emails")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getEmails(request: HttpRequest<*>, @PathVariable @NotBlank folderId: String): HttpResponse<List<EmailK>> {
        return HttpResponse.ok(emailService.getEmails(folderId, jwt(request)))
    }

    @Get("/{folderId}/emails/{emailId}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getEmail(
        request: HttpRequest<*>,
        @PathVariable @NotBlank folderId: String,
        @PathVariable @NotBlank emailId: String
    ): HttpResponse<EmailK> {
        return HttpResponse.ok(
            emailService.getEmail(folderId, emailId, jwt(request)) ?: throw HttpStatusException(
                HttpStatus.NOT_FOUND,
                "Email with id <$emailId> not found."
            )
        )
    }

    @Get("/{folderId}/emails/{emailId}/content")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    open fun getEmailContent(
        request: HttpRequest<*>,
        @PathVariable @NotBlank folderId: String,
        @PathVariable @NotBlank emailId: String
    ): HttpResponse<StreamedFile> {
        return HttpResponse.ok(
            emailService.getEmailContent(folderId, emailId, jwt(request))
                ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Email with id <$emailId> not found.")
        )
    }

    @Get("/{folderName}/emails/{emailId}/attachments")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getAttachments(
        request: HttpRequest<*>,
        @PathVariable @NotBlank folderName: String,
        @PathVariable @NotBlank emailId: String
    ): HttpResponse<List<AttachmentK>> {
        return HttpResponse.ok(attachmentService.getAttachments(folderName, emailId, jwt(request)))
    }

    @Get("/{folderName}/emails/{emailId}/attachments/{attachmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    open fun getAttachment(
        request: HttpRequest<*>,
        @PathVariable @NotBlank folderName: String,
        @PathVariable @NotBlank emailId: String,
        @PathVariable @NotBlank attachmentId: String
    ): HttpResponse<AttachmentK> {
        return HttpResponse.ok(
            attachmentService.getAttachment(folderName, emailId, attachmentId, jwt(request))
        )
    }

    @Get("/{folderName}/emails/{emailId}/attachments/{attachmentId}/content")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    open fun getAttachmentContent(
        request: HttpRequest<*>,
        @PathVariable @NotBlank folderName: String,
        @PathVariable @NotBlank emailId: String,
        @PathVariable @NotBlank attachmentId: String
    ): HttpResponse<StreamedFile> {
        return HttpResponse.ok(
            attachmentService.getAttachmentContent(folderName, emailId, attachmentId, jwt(request))
        )
    }

    private fun jwt(request: HttpRequest<*>): String {
        return request.getAttribute("jwt").map { it.toString() }
            .orElseThrow {
                HttpStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No authentication token provided for secured endpoint."
                )
            }
    }
}