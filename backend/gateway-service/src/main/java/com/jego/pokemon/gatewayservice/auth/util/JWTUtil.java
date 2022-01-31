package com.jego.pokemon.gatewayservice.auth.util;

//import academy.digitallab.store.gateway.service.RedisService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class JWTUtil implements Serializable {

    static RSAPrivateKey privateKey;
    static RSAPublicKey publicKey;

    public Boolean validateToken(String token) {

        if (token != null) {
            try {
                publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(Constant.PUBLIC_KEY_FILE_RSA, Constant.ALGORIHM_RSA);
                RSAKeyProvider provider = RSAAlgorithm.providerForKeys(publicKey, privateKey);
                DecodedJWT jwt =  JWT.require(Algorithm.RSA256 ( provider ) )
                        //.withSubject(subject)
                        .build()
                        .verify(token.replace(Constant.TOKEN_PREFIX, StringUtils.EMPTY));
                return jwt!=null;
                //return !redisService.existToken(token);
            }catch (JWTDecodeException e){
                System.out.println(e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public String getUsernameFromToken(String token) {

        if (token != null) {
            String user=null;
            try {
                publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(Constant.PUBLIC_KEY_FILE_RSA, Constant.ALGORIHM_RSA);
                RSAKeyProvider provider = RSAAlgorithm.providerForKeys(publicKey, privateKey);
                DecodedJWT jwt = JWT.require(Algorithm.RSA256 ( provider ) )
                        //.withSubject(subject)
                        .build()
                        .verify(token.replace(Constant.TOKEN_PREFIX, StringUtils.EMPTY));
                user =jwt.getSubject();
                return user;
            }catch (JWTDecodeException e){
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}