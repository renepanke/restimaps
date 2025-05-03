package io.github.renepanke.restimaps.service;

import io.github.renepanke.restimaps.exceptions.checked.EmailsNotRetrievableException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotFoundException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotOpenableException;
import io.github.renepanke.restimaps.exceptions.checked.FoldersNotRetrievableException;
import io.github.renepanke.restimaps.mapper.FolderMapper;
import io.github.renepanke.restimaps.model.Folder;
import io.github.renepanke.restimaps.repository.FolderRepository;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Objects;

@Bean
@Singleton
public class FolderService {

    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Inject
    public FolderService(FolderRepository folderRepository, FolderMapper folderMapper) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
    }

    public List<Folder> getFolders(String jwt) throws FoldersNotRetrievableException {
        return this.folderRepository.getFolders(jwt).stream()
                .map(this.folderMapper::mapWithoutEmailDetails)
                .filter(Objects::nonNull)
                .toList();
    }

    public Folder getFolder(String folderName, String jwt) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException {
        return this.folderMapper.map(this.folderRepository.getFolder(folderName, jwt));
    }

}
