package io.github.renepanke.restimaps.service

import io.github.renepanke.restimaps.mapper.AttachmentMapperK
import io.github.renepanke.restimaps.model.AttachmentK
import io.github.renepanke.restimaps.repository.AttachmentRepositoryK
import io.github.renepanke.restimaps.repository.EmailRepositoryK
import io.github.renepanke.restimaps.repository.FolderRepositoryK
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.server.types.files.StreamedFile
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class AttachmentServiceK {

    private val attachmentMapper: AttachmentMapperK
    private val attachmentRepository: AttachmentRepositoryK
    private val emailRepository: EmailRepositoryK
    private val folderRepository: FolderRepositoryK

    @Inject
    constructor(
        attachmentMapper: AttachmentMapperK,
        attachmentRepository: AttachmentRepositoryK,
        emailRepository: EmailRepositoryK,
        folderRepository: FolderRepositoryK,
    ) {
        this.attachmentMapper = attachmentMapper
        this.attachmentRepository = attachmentRepository
        this.emailRepository = emailRepository
        this.folderRepository = folderRepository
    }

    fun getAttachments(folderId: String, emailId: String, jwt: String): List<AttachmentK> {
        val folder = folderRepository.getFolder(folderId, jwt)
        val message = emailRepository.getEmail(folder, emailId)
        if (message.isEmpty) {
            throw HttpStatusException(HttpStatus.NOT_FOUND, "Message with id <$emailId> not found.")
        }
        return attachmentRepository.getAttachments(message.get())
            .map { this.attachmentMapper.map(it.key, it.value) }
    }

    fun getAttachment(folderId: String, emailId: String, attachmentId: String, jwt: String): AttachmentK {
        return Optional.ofNullable(
            this.getAttachments(folderId, emailId, jwt)
                .find { it.id == attachmentId }).orElseThrow() {
            HttpStatusException(HttpStatus.NOT_FOUND, "Attachment with id <$attachmentId> not found.")
        }
    }

    fun getAttachmentContent(folderId: String, emailId: String, attachmentId: String, jwt: String): StreamedFile {
        val folder = folderRepository.getFolder(folderId, jwt)
        val message = emailRepository.getEmail(folder, emailId)
        if (message.isEmpty) {
            throw HttpStatusException(HttpStatus.NOT_FOUND, "Message with id <$emailId> not found.")
        }
        return attachmentRepository.getAttachmentContent(attachmentId, message.get())
    }
}