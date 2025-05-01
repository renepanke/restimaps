package io.github.renepanke.restimaps.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public final class JWT {

    private static final byte[] SHARED_SECRET = "11111111111111111111111111111111".getBytes(StandardCharsets.UTF_8);

    private JWT() {
        throw new AssertionError();
    }

    public static String createJwt(String host, int port, String username, String password) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("host", host)
                .claim("port", port)
                .claim("username", username)
                .claim("password", password)
                .build();

        JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM);
        JWEObject jweObject = new JWEObject(header, new Payload(claimsSet.toJSONObject()));
        jweObject.encrypt(new DirectEncrypter(SHARED_SECRET));
        return jweObject.serialize();
    }

    public static JWTClaimsSet parseJwt(String token) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(token);
        jweObject.decrypt(new DirectDecrypter(SHARED_SECRET));
        return JWTClaimsSet.parse(jweObject.getPayload().toJSONObject());
    }

}
