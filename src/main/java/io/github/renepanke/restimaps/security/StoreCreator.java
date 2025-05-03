package io.github.renepanke.restimaps.security;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;

import java.util.Optional;
import java.util.Properties;

public final class StoreCreator {

    private StoreCreator() {
        throw new AssertionError();
    }

    public static Optional<Store> createStore(String host, int port, String username, String password) {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", host);
            props.put("mail.imaps.port", port);
            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.ssl.require", "true");
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect(username, password);
            return Optional.of(store);
        } catch (MessagingException | IllegalStateException e) {
            return Optional.empty();
        }
    }

}
