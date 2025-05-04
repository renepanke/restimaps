package io.github.renepanke.restimaps.security

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Singleton
@Filter("/restimaps**/**")
class AuthenticationFilterK : HttpServerFilter {

    private val securityCache: SecurityCacheK
    private val jwt: JWTK

    @Inject
    constructor(securityCache: SecurityCacheK, jwt: JWTK) {
        this.securityCache = securityCache
        this.jwt = jwt
    }

    override fun doFilter(
        request: HttpRequest<*>?,
        chain: ServerFilterChain?
    ): Publisher<MutableHttpResponse<*>?>? {
        var authorization = request?.headers?.authorization?.orElse(null)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Mono.just(HttpResponse.unauthorized<Any>().toMutableResponse())
        }
        authorization = authorization.substring("Bearer ".length)

        val store = securityCache.get(authorization)
        if (store != null) {
            request?.setAttribute("jwt", authorization)
            return chain?.proceed(request)
        }

        val claimsSet = jwt.parseJwt(authorization)
        val host = claimsSet.getStringClaim("host")
        val port = claimsSet.getIntegerClaim("port")
        val username = claimsSet.getStringClaim("username")
        val password = claimsSet.getStringClaim("password")
        val newStore = createStore(host, port, username, password)
        securityCache.store(authorization, newStore)
        request?.setAttribute("jwt", authorization)
        return chain?.proceed(request)


    }
}