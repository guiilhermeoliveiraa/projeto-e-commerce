package com.javacore.spring_api_app.domain;

public final class EmailNormalizer {

    private EmailNormalizer() {}

    public static String normalize(String email) {
        return email.trim().toLowerCase();
    }
}
