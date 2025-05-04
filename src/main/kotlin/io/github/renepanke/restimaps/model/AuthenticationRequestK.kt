package io.github.renepanke.restimaps.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable.Deserializable
data class AuthenticationRequestK(var host: String, var port: Int, var username: String, var password: String)
