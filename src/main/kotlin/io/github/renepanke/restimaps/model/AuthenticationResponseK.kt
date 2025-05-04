package io.github.renepanke.restimaps.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
data class AuthenticationResponseK(var token: String, var expiryTimestamp: String)