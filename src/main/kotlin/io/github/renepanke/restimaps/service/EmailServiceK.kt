package io.github.renepanke.restimaps.service

import io.github.renepanke.restimaps.mapper.EmailMapperK
import io.github.renepanke.restimaps.model.EmailK
import io.github.renepanke.restimaps.repository.EmailRepositoryK
import io.github.renepanke.restimaps.repository.FolderRepositoryK
import io.micronaut.http.MediaType
import io.micronaut.http.server.types.files.StreamedFile
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.io.PipedInputStream
import java.io.PipedOutputStream

@Singleton
class EmailServiceK {

    private val emailRepository: EmailRepositoryK
    private val folderRepository: FolderRepositoryK
    private val emailMapper: EmailMapperK

    @Inject
    constructor(emailRepository: EmailRepositoryK, folderRepository: FolderRepositoryK, emailMapper: EmailMapperK) {
        this.emailRepository = emailRepository
        this.folderRepository = folderRepository
        this.emailMapper = emailMapper
    }

    fun getEmails(folderId: String, jwt: String): List<EmailK> {
        val folder = folderRepository.getFolder(folderId, jwt)
        return emailRepository.getEmails(folder)
            .map { emailMapper.map(it) }
    }

    fun getEmail(folderId: String, emailId: String, jwt: String): EmailK? {
        val folder = folderRepository.getFolder(folderId, jwt)
        return emailRepository.getEmail(folder, emailId)
            .map { emailMapper.map(it) }
            .orElse(null)
    }

    fun getEmailContent(folderId: String, emailId: String, jwt: String): StreamedFile? {
        val folder = folderRepository.getFolder(folderId, jwt)
        val message = emailRepository.getEmail(folder, emailId)
        if (message.isEmpty) {
            return null
        }
        val input = PipedInputStream()
        val output = PipedOutputStream(input)
        Thread {
            try {
                message.get().writeTo(output)
            } catch (e: Exception) {
                throw RuntimeException(e)
            } finally {
                output.close()
            }
        }.start()
        return StreamedFile(input, MediaType("message/rfc822"))
    }
}