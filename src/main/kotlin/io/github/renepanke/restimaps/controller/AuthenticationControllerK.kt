package io.github.renepanke.restimaps.controller

import io.github.renepanke.restimaps.model.AuthenticationRequestK
import io.github.renepanke.restimaps.model.AuthenticationResponseK
import io.github.renepanke.restimaps.service.AuthenticationServiceK
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import jakarta.inject.Inject

@Controller("/auth")
open class AuthenticationControllerK {

    private val authenticationService: AuthenticationServiceK

    @Inject
    constructor(authenticationService: AuthenticationServiceK) {
        this.authenticationService = authenticationService
    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    open fun authenticate(@Body request: AuthenticationRequestK): HttpResponse<AuthenticationResponseK> {
        val jwtAndExpiry =
            authenticationService.authenticate(request.host, request.port, request.username, request.password)

        val authenticationResponse = AuthenticationResponseK(jwtAndExpiry.token, jwtAndExpiry.expiryTimestamp)
        return HttpResponse.ok(authenticationResponse)
    }
}