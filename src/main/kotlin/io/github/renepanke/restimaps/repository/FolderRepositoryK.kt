package io.github.renepanke.restimaps.repository

import io.github.renepanke.restimaps.security.SecurityCacheK
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.mail.Folder

@Singleton
class FolderRepositoryK {

    private val securityCache: SecurityCacheK

    @Inject
    constructor(securityCache: SecurityCacheK) {
        this.securityCache = securityCache
    }

    fun getFolder(folderName: String, jwt: String): Folder {
        return securityCache.getThrowing(jwt).defaultFolder.getFolder(folderName)
    }

    fun getFolders(jwt: String): List<Folder> {
        return securityCache.getThrowing(jwt).defaultFolder.list()
            .filter { it.type and Folder.HOLDS_FOLDERS != 0 }
    }
}
