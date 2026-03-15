package com.javacore.spring_api_app.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}
