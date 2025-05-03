package io.github.renepanke.restimaps.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public final class JWT {

    // 256-bit key (32 bytes) for A256GCM
    private static final byte[] SHARED_SECRET = "11111111111111111111111111111111".getBytes(StandardCharsets.UTF_8);

    private JWT() {
        throw new AssertionError("No instances for you!");
    }

    public static String createJwt(String host, int port, String username, String password) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("host", host)
                .claim("port", port)
                .claim("username", username)
                .claim("password", password)
                .build();

        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                .contentType("JWT") // Optional, recommended
                .build();

        EncryptedJWT encryptedJWT = new EncryptedJWT(header, claimsSet);
        encryptedJWT.encrypt(new DirectEncrypter(SHARED_SECRET));

        return encryptedJWT.serialize();
    }

    public static JWTClaimsSet parseJwt(String token) throws ParseException, JOSEException {
        EncryptedJWT encryptedJWT = EncryptedJWT.parse(token);
        encryptedJWT.decrypt(new DirectDecrypter(SHARED_SECRET));
        return encryptedJWT.getJWTClaimsSet();
    }
}
