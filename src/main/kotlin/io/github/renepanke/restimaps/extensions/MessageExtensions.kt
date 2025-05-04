package io.github.renepanke.restimaps.extensions

import io.micronaut.http.MediaType
import jakarta.mail.Flags
import jakarta.mail.Message
import jakarta.mail.Multipart
import jakarta.mail.Part
import jakarta.mail.internet.InternetAddress

private const val READ_RECEIPT_HEADER = "Disposition-Notification-To"
private const val INTERNET_MESSAGE_ID_HEADER = "Message-ID"

fun Message.isReadReceiptRequested(): Boolean {
    return !this.getHeader(READ_RECEIPT_HEADER).isNullOrEmpty()
}

fun Message.getRecipientsAsString(recipientType: Message.RecipientType): List<String> {
    return this.getRecipients(recipientType)?.map { it as InternetAddress }?.map { it.address!! } ?: emptyList()
}

fun Message.getInternetMessageId(): String {
    return this.getHeader(INTERNET_MESSAGE_ID_HEADER)[0]
}

fun Message.getPlainContent(): String? {
    return extractContent(this, MediaType.TEXT_PLAIN)
}

fun Message.getHtmlContent(): String? {
    return extractContent(this, MediaType.TEXT_HTML)
}

fun Message.isRead(): Boolean {
    return this.flags.contains(Flags.Flag.SEEN)
}

private fun extractContent(part: Part, mediaType: String): String? {
    if (part.isMimeType(mediaType)) {
        return part.content as String
    }
    if (part.isMimeType("multipart/*")) {
        val mp = part.content as Multipart
        for (x in 0 until mp.count) {
            val bodyPart = mp.getBodyPart(x)
            val extractedContent = extractContent(bodyPart, mediaType)
            if (extractedContent != null) {
                return extractedContent
            }
        }
    }
    return null
}