package io.github.renepanke.restimaps.security;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.mail.Store;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static io.github.renepanke.restimaps.security.JWT.createJwt;
import static io.github.renepanke.restimaps.security.JWT.parseJwt;

@Bean
@Singleton
@Filter("/restimap**/**")
public class AuthenticationFilter implements HttpServerFilter {

    private final SecurityCache securityCache;

    @Inject
    public AuthenticationFilter(SecurityCache securityCache) {
        this.securityCache = securityCache;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {

        String authorization = request.getHeaders().getAuthorization().orElse(null);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Mono.just(HttpResponse.unauthorized().toMutableResponse());
        }
        Optional<Store> optionalStore = securityCache.get(authorization);
        if (optionalStore.isPresent()) {
            return chain.proceed(request);
        }
        try {
            authorization = authorization.substring("Bearer ".length());
            JWTClaimsSet claimsSet = parseJwt(authorization);
            String host = claimsSet.getStringClaim("host");
            int port = claimsSet.getIntegerClaim("port");
            String username = claimsSet.getStringClaim("username");
            String password = claimsSet.getStringClaim("password");
            Optional<Store> newStore = StoreCreator.createStore(host, port, username, password);
            if (newStore.isEmpty()) {
                return Mono.just(HttpResponse.unauthorized().toMutableResponse());
            }
            securityCache.store(authorization, newStore.get());
            request.setAttribute("jwt", authorization);
            return chain.proceed(request);
        } catch (Exception e) {
            return Mono.just(HttpResponse.unauthorized().toMutableResponse().body(e.getMessage()));
        }
    }

    public static void main(String[] args) throws Exception {
        String token = createJwt("localhost", 5432, "user", "secret");
        System.out.println("Token: " + token);

        JWTClaimsSet claims = parseJwt(token);
        System.out.println("Parsed claims: " + claims.toJSONObject());
    }

}
