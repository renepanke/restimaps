package io.github.renepanke.restimaps.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
data class AttachmentK(
    var contentDisposition: String?,
    var id: String?,
    var mediaType: String?,
    var name: String?,
    var sizeInBytes: Long?
)
