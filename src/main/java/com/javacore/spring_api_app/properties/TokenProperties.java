package com.javacore.spring_api_app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "spring.jwt")
public record TokenProperties(
        RSAPrivateKey privateKey,
        RSAPublicKey publicKey
) {
}
