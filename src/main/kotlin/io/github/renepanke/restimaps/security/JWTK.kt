package io.github.renepanke.restimaps.security

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.crypto.DirectDecrypter
import com.nimbusds.jose.crypto.DirectEncrypter
import com.nimbusds.jwt.EncryptedJWT
import com.nimbusds.jwt.JWTClaimsSet
import jakarta.inject.Singleton
import java.nio.charset.StandardCharsets

@Singleton
class JWTK {

    private val sharedSecret: ByteArray = "11111111111111111111111111111111".toByteArray(StandardCharsets.UTF_8);

    fun createJwt(host: String, port: Int, username: String, password: String): String {
        val claimsSet = JWTClaimsSet.Builder()
            .claim("host", host)
            .claim("port", port)
            .claim("username", username)
            .claim("password", password)
            .build()

        val header = JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
            .contentType("JWT")
            .build()

        val encryptedJWT = EncryptedJWT(header, claimsSet)
        encryptedJWT.encrypt(DirectEncrypter(sharedSecret))
        return encryptedJWT.serialize()
    }

    fun parseJwt(jwt: String): JWTClaimsSet {
        val encryptedJWT = EncryptedJWT.parse(jwt)
        encryptedJWT.decrypt(DirectDecrypter(sharedSecret))
        return encryptedJWT.jwtClaimsSet;
    }
}
