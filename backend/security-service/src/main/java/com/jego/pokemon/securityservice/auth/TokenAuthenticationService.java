package com.jego.pokemon.securityservice.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.jego.pokemon.securityservice.auth.util.Constant;
import com.jego.pokemon.securityservice.auth.util.PemUtils;
import com.jego.pokemon.securityservice.auth.util.RSAAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
@Slf4j
public class TokenAuthenticationService {

    static RSAPrivateKey privateKey;
    static RSAPublicKey publicKey;

    static void addAuthentication(HttpServletResponse res, String username) {

        String token= StringUtils.EMPTY;
        try {
            privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(Constant.PRIVATE_KEY_FILE_RSA, "RSA");
            publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(Constant.PUBLIC_KEY_FILE_RSA, "RSA");
            token = JWT.create()
                    .withSubject(username)
                    .withJWTId(UUID.randomUUID().toString() )
                    .withKeyId( Constant.KEY_ID_JWT)
                    .withAudience(Constant.KEY_AUDIENCE)
                    .withNotBefore(new Date())
                    .withIssuer(Constant.KEY_ISSUER)
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constant.EXPIRATIONTIME))
                    .sign(Algorithm.RSA256(publicKey, privateKey)
                    );

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        res.addHeader( Constant.TOKEN_HEADER_STRING,  Constant.TOKEN_PREFIX + token);

    }

    static Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(Constant.TOKEN_HEADER_STRING);

        RSAKey key;

        if (token != null) {
            // parse the token.
            String user=null;
            try {

                privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(Constant.PRIVATE_KEY_FILE_RSA, "RSA");
                publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(Constant.PUBLIC_KEY_FILE_RSA, "RSA");

                RSAKeyProvider provider = RSAAlgorithm.providerForKeys(publicKey, privateKey);

                DecodedJWT jwt = JWT.require(Algorithm.RSA256 ( provider ) )
                        .build()
                        .verify(token.replace(Constant.TOKEN_PREFIX, StringUtils.EMPTY));

                user =jwt.getSubject();

            }catch (JWTDecodeException e){
                log.info(e.getMessage());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                return user != null ?
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
            }


        }
        return null;
    }

}
