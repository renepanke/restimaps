package io.github.renepanke.restimaps.service

import io.github.renepanke.restimaps.model.AuthenticationResponseK
import io.github.renepanke.restimaps.security.JWTK
import io.github.renepanke.restimaps.security.SecurityCacheK
import io.github.renepanke.restimaps.security.createStore
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Singleton
class AuthenticationServiceK {

    private val securityCache: SecurityCacheK
    private val jwtk: JWTK

    @Inject
    constructor(securityCache: SecurityCacheK, jwtk: JWTK) {
        this.securityCache = securityCache
        this.jwtk = jwtk
    }

    fun authenticate(host: String, port: Int, username: String, password: String): AuthenticationResponseK {
        val store = createStore(host, port, username, password)
        val jwt = jwtk.createJwt(host, port, username, password)
        securityCache.store(jwt, store)
        val expiryTimestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return AuthenticationResponseK(jwt, expiryTimestamp)
    }

}