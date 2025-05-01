package io.github.renepanke.restimaps.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties("imaps")
public class ImapsConfiguration {

    @NotBlank
    private String host;

    @NotNull
    @Min(1)
    @Max(65535)
    private int port;

    @NotBlank
    private String username;

    @Nullable
    private String password;

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
