package io.github.renepanke.restimaps.security

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.RemovalCause
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import jakarta.mail.Store
import java.time.Duration
import java.util.*

val expirationDuration: Duration = Duration.ofMinutes(15)

@Singleton
class SecurityCacheK {

    private val cache: Cache<String, Store> = Caffeine.newBuilder()
        .expireAfterWrite(expirationDuration)
        .removalListener { _: String?, value: Store?, _: RemovalCause ->
            val store = value as Store
            if (store.isConnected) {
                store.close()
            }
        }.build()


    fun store(jwt: String, store: Store) {
        cache.put(jwt, store)
    }

    fun get(jwt: String): Store? {
        return cache.getIfPresent(jwt)
    }

    fun getThrowing(jwt: String): Store {
        return Optional.ofNullable(get(jwt)).orElseThrow() {
            HttpStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT: $jwt")
        }
    }
}

