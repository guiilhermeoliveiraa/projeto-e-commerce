package com.javacore.spring_api_app.dto.request.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "Primeiro nome é obrigatório")
        @Size(min = 2, message = "Primeiro nome deve conter ao menos 2 caracteres")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "Primeiro nome é inválido")
        String firstName,

        @NotBlank(message = "Ultimo nome é obrigatório")
        @Size(min = 2, message = "Ultimo nome deve conter ao menos 2 caracteres")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "Ultimo nome é inválido")
        String lastName,

        @NotBlank(message = "Email é obrigatório")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email é inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve conter ao menos 8 caracteres")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
                message = "Senha é inválida")
        String password,

        @NotBlank(message = "Confirmar senha é obrigatório")
        String confirmPassword
) {
}
