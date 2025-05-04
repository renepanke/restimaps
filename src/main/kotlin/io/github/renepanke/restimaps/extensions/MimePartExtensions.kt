package io.github.renepanke.restimaps.extensions

import jakarta.mail.internet.ContentType
import jakarta.mail.internet.MimePart

fun MimePart.isMediaType(): Boolean {
    return ContentType(this.dataHandler.contentType).match("multipart/*")
}
