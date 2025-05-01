package io.github.renepanke.restimaps.service;

import com.nimbusds.jose.JOSEException;
import io.github.renepanke.restimaps.lang.Tuple;
import io.github.renepanke.restimaps.security.JWT;
import io.github.renepanke.restimaps.security.SecurityCache;
import io.github.renepanke.restimaps.security.StoreCreator;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Store;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Bean
@Singleton
public class AuthenticationService {

    private final SecurityCache securityCache;

    @Inject
    public AuthenticationService(SecurityCache securityCache) {
        this.securityCache = securityCache;
    }

    public Tuple<String, String> authenticate(String host, int port, String username, String password) throws JOSEException {
        Store store = StoreCreator.createStore(host, port, username, password).orElseThrow(() -> new RuntimeException());
        String jwt = JWT.createJwt(host, port, username, password);
        this.securityCache.store(jwt, store);
        String expiryTimestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return Tuple.of(jwt, expiryTimestamp);
    }
}
