package com.javacore.spring_api_app.dto.request.sendgrid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendGridRequest(
        @NotBlank
        @Email
        String to,

        @NotBlank
        String subject,

        @NotBlank
        String message
) {
}
