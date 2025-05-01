package io.github.renepanke.restimaps.repository;

import io.github.renepanke.restimaps.exceptions.checked.FolderNotCloseableException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotFoundException;
import io.github.renepanke.restimaps.exceptions.checked.FolderNotOpenableException;
import io.github.renepanke.restimaps.exceptions.checked.FoldersNotRetrievableException;
import io.github.renepanke.restimaps.security.SecurityCache;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import org.eclipse.angus.mail.imap.IMAPFolder;

import java.util.ArrayList;
import java.util.List;

@Bean
@Singleton
public class FolderRepository {

    private final SecurityCache securityCache;

    @Inject
    public FolderRepository(SecurityCache securityCache) {
        this.securityCache = securityCache;
    }

    public Folder getFolder(String folderName, String jwt) throws FolderNotFoundException {
        try {
            return this.storeProvider.getStore().getFolder(folderName);
        } catch (MessagingException e) {
            throw new FolderNotFoundException("Folder <" + "> hasn't been found.", e);
        }
    }

    public List<Folder> getFolders() throws FoldersNotRetrievableException {
        IMAPFolder imapFolder = null;
        imapFolder.getUID()
        List<Folder> foldersList = new ArrayList<>();
        try {
            Folder[] folders = this.storeProvider.getStore().getDefaultFolder().list();
            for (Folder folder : folders) {
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
