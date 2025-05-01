package io.github.renepanke.restimaps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;

@JsonTypeName("authenticationResponse")
public class AuthenticationResponse {

    @JsonProperty("token")
    @NotBlank
    private String token;

    @JsonProperty("expiryTimestamp")
    @NotBlank
    private String expiryTimestamp;

    public AuthenticationResponse() {
    }

    public String getExpiryTimestamp() {
        return expiryTimestamp;
    }

    public void setExpiryTimestamp(String expiryTimestamp) {
        this.expiryTimestamp = expiryTimestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
