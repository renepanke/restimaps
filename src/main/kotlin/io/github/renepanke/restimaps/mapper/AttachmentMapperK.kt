package io.github.renepanke.restimaps.mapper

import io.github.renepanke.restimaps.model.AttachmentK
import jakarta.activation.DataSource
import jakarta.inject.Singleton

@Singleton
class AttachmentMapperK {

    fun map(attachmentId: String, dataSource: DataSource): AttachmentK {
        dataSource.inputStream.use { input ->
            return AttachmentK(
                null,
                attachmentId,
                dataSource.contentType,
                dataSource.name,
                input.readAllBytes().size.toLong()
            )
        }
    }
}