package io.github.renepanke.restimaps.service

import io.github.renepanke.restimaps.mapper.FolderMapperK
import io.github.renepanke.restimaps.model.FolderK
import io.github.renepanke.restimaps.repository.FolderRepositoryK
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class FolderServiceK {

    private val folderRepository: FolderRepositoryK
    private val folderMapper: FolderMapperK

    @Inject
    constructor(folderRepository: FolderRepositoryK, folderMapper: FolderMapperK) {
        this.folderRepository = folderRepository
        this.folderMapper = folderMapper
    }

    fun getFolders(jwt: String): List<FolderK> {
        return folderRepository.getFolders(jwt)
            .map { folderMapper.mapWithoutEmailDetails(it) }
            .toList()
    }

    fun getFolder(folderName: String, jwt: String): FolderK {
        return folderMapper.map(folderRepository.getFolder(folderName, jwt))
    }
}