package io.github.renepanke.restimaps.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

@JsonTypeName("authenticationRequest")
public class AuthenticationRequest {

    @JsonProperty("host")
    @NotBlank
    private String host;

    @JsonProperty("port")
    @NotBlank
    private int port;

    @JsonProperty("username")
    @NotBlank
    private String username;

    @JsonProperty("password")
    @Nullable
    private String password;

    public AuthenticationRequest() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }
}
