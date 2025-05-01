package io.github.renepanke.restimaps.repository;

import io.github.renepanke.restimaps.exceptions.checked.*;
import io.github.renepanke.restimaps.exceptions.unchecked.FolderNotOpenedException;
import io.github.renepanke.restimaps.lang.Tuple;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Singleton;
import jakarta.mail.FetchProfile;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Bean
@Singleton
public class EmailRepository {

    // TODO:    Use fetch profile properly
    private static final FetchProfile FETCH_PROFILE = computeFetchProfile();

    public List<Message> getEmails(Folder folder) throws FolderNotOpenableException, FolderNotFoundException, EmailsNotRetrievableException {
        if (folder == null) {
            return List.of();
        }
        FolderRepository.openFolderIfClosed(folder);
        try {
            Message[] messageArray = folder.getMessages();
            folder.fetch(messageArray, FETCH_PROFILE);
            return Arrays.stream(messageArray).toList();
        } catch (jakarta.mail.FolderNotFoundException e) {
            throw new FolderNotFoundException(e);
        } catch (MessagingException e) {
            throw new EmailsNotRetrievableException(e);
        } catch (IllegalStateException e) {
            throw new FolderNotOpenedException(e);
        }
    }

    public List<String> getEmailIds(Folder folder) throws EmailsNotRetrievableException, FolderNotOpenableException {
        if (folder == null) {
            return List.of();
        }
        FolderRepository.openFolderIfClosed(folder);
        try {
            Message[] messageArray = folder.getMessages();
            folder.fetch(messageArray, FETCH_PROFILE);
            return Arrays.stream(messageArray).map(it -> {
                try {
                    return getInternetMessageId(it);
                } catch (EmailIdNotFoundException | EmailIdNotRetrievableException e) {
                    return null;
                }
            }).toList();
        } catch (MessagingException e) {
            throw new EmailsNotRetrievableException(e);
        }
    }

    public Optional<Message> getEmail(Folder folder, String internetMessageId) throws FolderNotOpenableException, FolderNotFoundException, EmailsNotRetrievableException {
        if (folder == null || internetMessageId == null) {
            return Optional.empty();
        }
        FolderRepository.openFolderIfClosed(folder);
        try {
            return Arrays.stream(folder.getMessages())
                    .map(it -> {
                        try {
                            return Tuple.of(getInternetMessageId(it), it);
                        } catch (EmailIdNotFoundException | EmailIdNotRetrievableException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(internetMessageId, it.getLeft()))
                    .map(Tuple::getRight)
                    .findFirst();
        } catch (jakarta.mail.FolderNotFoundException e) {
            throw new FolderNotFoundException(e);
        } catch (MessagingException e) {
            throw new EmailsNotRetrievableException(e);
        } catch (IllegalStateException e) {
            throw new FolderNotOpenedException(e);
        }

    }

    private static FetchProfile computeFetchProfile() {
        FetchProfile fetchProfile = new FetchProfile();
        fetchProfile.add(FetchProfile.Item.ENVELOPE);
        fetchProfile.add(FetchProfile.Item.CONTENT_INFO);
        fetchProfile.add(FetchProfile.Item.FLAGS);
        fetchProfile.add(FetchProfile.Item.SIZE);
        return fetchProfile;
    }

    public static String getInternetMessageId(Message message) throws EmailIdNotRetrievableException, EmailIdNotFoundException {
        String[] messageIds = null;
        try {
            messageIds = message.getHeader("Message-ID");
        } catch (MessagingException e) {
            throw new EmailIdNotRetrievableException(e);
        }
        if (messageIds != null && messageIds.length > 0) {
            return messageIds[0];
        }
        throw new EmailIdNotFoundException("No internet message ID could be found for given message.");
    }
}
