package io.github.renepanke.restimaps.repository

import io.github.renepanke.restimaps.extensions.isMediaType
import io.micronaut.http.MediaType
import io.micronaut.http.server.types.files.StreamedFile
import jakarta.activation.DataSource
import jakarta.inject.Singleton
import jakarta.mail.Message
import jakarta.mail.Multipart
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimePart
import java.util.*
import java.util.regex.Pattern

@Singleton
class AttachmentRepositoryK {

    fun getAttachments(msg: Message): Map<String, DataSource> {
        return getAttachmentsInternal(msg)
    }

    fun getAttachment(attachmentId: String, msg: Message): Optional<DataSource> {
        return Optional.ofNullable(this.getAttachments(msg).filter {
            it.key == attachmentId
        }.map { it.value }
            .firstOrNull())
    }

    fun getAttachmentContent(attachmentId: String, msg: Message): StreamedFile {
        val dataSource = getAttachment(attachmentId, msg).orElseThrow()
        val contentType = dataSource.contentType ?: "application/octet-stream"
        return StreamedFile(dataSource.inputStream, MediaType(contentType))
    }

    private fun getAttachmentsInternal(msg: Message): Map<String, DataSource> {
        return getAttachmentsRecursive(msg as MimePart, mutableMapOf())
    }

    private fun getAttachmentsRecursive(part: MimePart, attachments: Map<String, DataSource>): Map<String, DataSource> {
        val recursiveAttachments = mutableMapOf<String, DataSource>()
        recursiveAttachments.putAll(attachments)
        if (part.isMediaType()) {
            val multipart = part.content as Multipart
            for (x in 0 until multipart.count) {
                recursiveAttachments.putAll(
                    getAttachmentsRecursive(
                        multipart.getBodyPart(x) as MimeBodyPart,
                        attachments
                    )
                )
            }
        } else {
            val contentId = stripContentId(part.contentID)
            if (contentId.isNullOrEmpty()) {
                return recursiveAttachments
            }
            val dataSource = createDataSource(part)
            recursiveAttachments[contentId] = dataSource
        }
        return recursiveAttachments
    }
}


fun createDataSource(mimePart: MimePart): DataSource {
    val dataSource = mimePart.dataHandler.dataSource
    return InputStreamDataSourceK(normalizeMediaType(dataSource.contentType), dataSource.inputStream, dataSource.name)
}

fun normalizeMediaType(contentType: String): String {
    return contentType.lowercase().indexOf(';')
        .let { if (it >= 0) contentType.substring(0, it) else contentType.lowercase() }
}

fun stripContentId(contentId: String?): String? {
    val regex = Pattern.compile("[<>]").toRegex()
    return contentId?.trim()?.replace(regex, "")
}