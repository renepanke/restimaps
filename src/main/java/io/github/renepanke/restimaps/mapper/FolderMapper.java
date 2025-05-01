package io.github.renepanke.restimaps.mapper;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.model.Folder;
import io.github.renepanke.restimaps.repository.EmailRepository;
import io.github.renepanke.restimaps.repository.FolderRepository;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Bean
@Singleton
public class FolderMapper {

    private final EmailRepository emailRepository;

    @Inject
    public FolderMapper(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public Folder mapWithoutEmailDetails(jakarta.mail.Folder f) {
        try {
            FolderRepository.openFolderIfClosed(f);
            Folder folder = new Folder();
            try {
                jakarta.mail.Folder[] childFolders = f.list();
                folder.setChildFolderCount(childFolders.length);
                folder.setChildFolderIds(Arrays.stream(childFolders).map(jakarta.mail.Folder::getName).toList());
                folder.setDisplayName(f.getName());
                folder.setId(f.getName());
                folder.setParentFolder(f.getParent().getName());
                return folder;
            } catch (MessagingException e) {
                throw new ChildFoldersNotRetrievableException(e);
            }
        } catch (FolderNotOpenableException | ChildFoldersNotRetrievableException e) {
            // TODO: Log
            return null;
        }
    }

    public Folder map(jakarta.mail.Folder f) throws FolderNotFoundException, EmailsNotRetrievableException, FolderNotOpenableException {
        Folder folder = mapWithoutEmailDetails(f);
        List<Message> emails = this.emailRepository.getEmails(f);
        folder.setItemCount(emails.size() + folder.getChildFolderCount());
        List<String> emailIds = emails.stream().map(it -> {
                    try {
                        return EmailRepository.getInternetMessageId(it);
                    } catch (EmailIdNotFoundException | EmailIdNotRetrievableException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        folder.setEmailIds(emailIds);
        return folder;
    }
}
