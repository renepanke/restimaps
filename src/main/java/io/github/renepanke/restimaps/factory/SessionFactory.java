package io.github.renepanke.restimaps.factory;

import io.github.renepanke.restimaps.configuration.ImapsConfiguration;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;

import java.util.Properties;

@Bean
@Factory
public class SessionFactory {

    private final ImapsConfiguration imapsConfiguration;

    @Inject
    public SessionFactory(ImapsConfiguration imapsConfiguration) {
        this.imapsConfiguration = imapsConfiguration;
    }

    @Bean
    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", this.imapsConfiguration.getHost());
        props.put("mail.imaps.port", this.imapsConfiguration.getPort());
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.ssl.require", "true");
        return Session.getInstance(props);
    }

    @Bean
    public Store getStore() throws MessagingException {
        Store store = this.getSession().getStore("imaps");
        store.connect(this.imapsConfiguration.getUsername(), this.imapsConfiguration.getPassword());
        return store;
    }
}
