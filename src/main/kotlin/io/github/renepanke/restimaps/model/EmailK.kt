package io.github.renepanke.restimaps.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
data class EmailK(
    var attachmentIds: List<String>?,
    var bcc: List<String>?,
    var cc: List<String>?,
    var creationTimestamp: String?,
    var folderId: String?,
    var from: String?,
    var internetMessageId: String?,
    var isRead: Boolean?,
    var isReadReceiptRequested: Boolean?,
    var lastModifiedTimestamp: String?,
    var plainContent: String?,
    var htmlContent: String?,
    var receivedTimestamp: String?,
    var replyTo: List<String>?,
    var sender: String?,
    var sentTimestamp: String?,
    var subject: String?,
    var to: List<String>?,
    var hasAttachments: Boolean?,
    var hasHtmlContent: Boolean?,
    var hasPlainContent: Boolean?
)
