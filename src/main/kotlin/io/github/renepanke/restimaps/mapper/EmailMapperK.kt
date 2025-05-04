package io.github.renepanke.restimaps.mapper

import io.github.renepanke.restimaps.extensions.*
import io.github.renepanke.restimaps.model.EmailK
import io.github.renepanke.restimaps.repository.AttachmentRepositoryK
import jakarta.inject.Inject
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage

class EmailMapperK {

    val attachmentRepository: AttachmentRepositoryK

    @Inject
    constructor(attachmentRepository: AttachmentRepositoryK) {
        this.attachmentRepository = attachmentRepository
    }

    fun map(message: Message): EmailK {
        val attachments = this.attachmentRepository.getAttachments(message)
        val plainContent = message.getPlainContent()
        val htmlContent = message.getHtmlContent()

        return EmailK(
            attachments.keys.toList(),
            message.getRecipientsAsString(Message.RecipientType.BCC),
            message.getRecipientsAsString(Message.RecipientType.CC),
            message.sentDate.toIso8601Utc(),
            message.folder.name,
            message.from?.map { it as InternetAddress }?.map { it.address }?.firstOrNull(),
            message.getInternetMessageId(),
            message.isRead(),
            message.isReadReceiptRequested(),
            message.sentDate.toIso8601Utc(),
            plainContent,
            htmlContent,
            message.receivedDate.toIso8601Utc(),
            message.replyTo?.map { it as InternetAddress }?.map { it.address }?.toList(),
            ((message as MimeMessage).sender as InternetAddress).address,
            message.sentDate.toIso8601Utc(),
            message.subject,
            message.getRecipientsAsString(Message.RecipientType.TO),
            attachments.isNotEmpty(),
            plainContent != null,
            htmlContent != null,
        )
    }
}
