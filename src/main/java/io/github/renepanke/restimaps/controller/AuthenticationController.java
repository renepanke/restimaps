package io.github.renepanke.restimaps.controller;

import com.nimbusds.jose.JOSEException;
import io.github.renepanke.restimaps.lang.Tuple;
import io.github.renepanke.restimaps.model.AuthenticationRequest;
import io.github.renepanke.restimaps.model.AuthenticationResponse;
import io.github.renepanke.restimaps.service.AuthenticationService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.zalando.problem.Problem;

@Controller("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<AuthenticationResponse> authenticate(@Body AuthenticationRequest request) {
        try {
            Tuple<String, String> jwtAndExpiry = this.authenticationService.authenticate(request.getHost(), request.getPort(), request.getUsername(), request.getPassword());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwtAndExpiry.getLeft());
            authenticationResponse.setExpiryTimestamp(jwtAndExpiry.getRight());
            return HttpResponse.ok(authenticationResponse);
        } catch (JOSEException e) {
            throw Problem.builder().build();
        }
    }
}
