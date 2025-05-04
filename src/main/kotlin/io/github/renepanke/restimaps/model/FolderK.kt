package io.github.renepanke.restimaps.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
data class FolderK(
    var childFolderCount: Int?,
    var childFolderIds: List<String>?,
    var displayName: String?,
    var id: String?,
    var parentFolder: String?,
    var itemCount: Int?,
    var emailIds: List<String>?
)
