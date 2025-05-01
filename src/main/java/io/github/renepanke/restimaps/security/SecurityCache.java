package io.github.renepanke.restimaps.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Singleton;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;

import java.time.Duration;
import java.util.Optional;

@Bean
@Singleton
public class SecurityCache {

    public static final Duration EXPIRATION_DURATION = Duration.ofMinutes(15);

    private final Cache<String, Store> cache = Caffeine.newBuilder()
            .expireAfterWrite(EXPIRATION_DURATION)
            .removalListener((key, value, exception) -> {
                Store store = ((Store) value);
                if (store != null && store.isConnected()) {
                    try {
                        store.close();
                    } catch (MessagingException e) {
                        // LOG
                    }
                }
            })
            .build();

    public void store(String jwt, Store store) {
        cache.put(jwt, store);
    }

    public Optional<Store> get(String jwt) {
        return Optional.ofNullable(this.cache.getIfPresent(jwt));
    }


}
