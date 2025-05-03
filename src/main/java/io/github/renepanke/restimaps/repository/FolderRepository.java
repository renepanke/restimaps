package io.github.renepanke.restimaps.repository;

import io.github.renepanke.restimaps.exceptions.checked.FolderNotCloseableException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotFoundException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotOpenableException;
import io.github.renepanke.restimaps.exceptions.checked.FoldersNotRetrievableException;
import io.github.renepanke.restimaps.lang.Tuple;
import io.github.renepanke.restimaps.security.SecurityCache;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@Singleton
public class FolderRepository {

    private final SecurityCache securityCache;

    @Inject
    public FolderRepository(SecurityCache securityCache) {
        this.securityCache = securityCache;
    }

    public Folder getFolder(String folderName, String jwt) throws FolderNotFoundException {
        Optional<Tuple<Folder, MessagingException>> result = this.securityCache.get(jwt).map(it -> {
            try {
                return Tuple.of(it.getFolder(folderName), null);
            } catch (MessagingException e) {
                return Tuple.of(null, e);
            }
        });
        if (result.isEmpty()) {
            throw new FolderNotFoundException(folderName);
        }
        if (result.get().getRight() != null) {
            throw new FolderNotFoundException("Folder <" + folderName + "> not found.", result.get().getRight());
        }
        return result.get().getLeft();
    }

    public List<Folder> getFolders(String jwt) throws FoldersNotRetrievableException {
        List<Folder> foldersList = new ArrayList<>();
        try {
            Optional<Tuple<Folder[], MessagingException>> result = this.securityCache.get(jwt).map(it -> {
                try {
                    return Tuple.of(it.getDefaultFolder().list(), null);
                } catch (MessagingException e) {
                    return Tuple.of(null, e);
                }
            });
            if (result.isEmpty()) {
                throw new FoldersNotRetrievableException("Folders not retrievable.");
            }
            if (result.get().getRight() != null) {
                throw new FoldersNotRetrievableException("Folders not retrievable.", result.get().getRight());
            }
            for (Folder folder : result.get().getLeft()) {
                if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
                    foldersList.add(folder);
                }
            }
            return foldersList;
        } catch (MessagingException e) {
            throw new FoldersNotRetrievableException(e);
        }
    }

    public static void openFolderIfClosed(Folder folder) throws FolderNotOpenableException {
        if (!folder.isOpen()) {
            try {
                folder.open(Folder.READ_WRITE);
            } catch (MessagingException e) {
                throw new FolderNotOpenableException(e);
            }
        }
    }

    public static void closeFolderIfOpened(Folder folder) throws FolderNotCloseableException {
        if (folder.isOpen()) {
            try {
                folder.close(false);
            } catch (MessagingException e) {
                throw new FolderNotCloseableException(e);
            }
        }
    }
}
