package io.github.renepanke.restimaps.mapper

import io.github.renepanke.restimaps.extensions.openIfClosed
import io.github.renepanke.restimaps.model.FolderK
import io.github.renepanke.restimaps.repository.EmailRepositoryK
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class FolderMapperK {

    private val emailRepository: EmailRepositoryK

    @Inject
    constructor(emailRepository: EmailRepositoryK) {
        this.emailRepository = emailRepository
    }

    fun mapWithoutEmailDetails(f: jakarta.mail.Folder): FolderK {
        f.openIfClosed()
        val childFolders = f.list()
        val childFolderIds = childFolders.map { it.name }.toList()
        return FolderK(childFolders.size, childFolderIds, f.name, f.name, f.parent?.name, null, null)
    }

    fun map(f: jakarta.mail.Folder): FolderK {
        f.openIfClosed()
        val folder = mapWithoutEmailDetails(f)
        val emailIds = emailRepository.getEmailIds(f)
        folder.itemCount = emailIds.size + (folder.childFolderCount ?: 0)
        folder.emailIds = emailIds
        return folder
    }
}