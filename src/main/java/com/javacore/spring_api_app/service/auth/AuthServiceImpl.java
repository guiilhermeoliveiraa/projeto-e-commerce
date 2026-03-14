package com.javacore.spring_api_app.service.auth;

import com.javacore.spring_api_app.domain.EmailNormalizer;
import com.javacore.spring_api_app.domain.NameNormalizer;
import com.javacore.spring_api_app.dto.request.RegisterUserRequest;
import com.javacore.spring_api_app.dto.response.RegisterUserResponse;
import com.javacore.spring_api_app.entity.User;
import com.javacore.spring_api_app.exception.custom.BusinessException;
import com.javacore.spring_api_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        String normalizedFirstName = NameNormalizer.normalize(request.firstName());
        String normalizedLastName = NameNormalizer.normalize(request.lastName());
        String normalizedEmail = EmailNormalizer.normalize(request.email());

        if (userRepository.existsByEmailAndDeletedFalse(normalizedEmail)) {
            throw new BusinessException("Este email já foi cadastrado");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new BusinessException("As senhas não coincidem");
        }

        User user = User.builder()
                .firstName(normalizedFirstName)
                .lastName(normalizedLastName)
                .email(normalizedEmail)
                .password(request.password())
                .build();

        return toResponse(userRepository.save(user));
    }

    private RegisterUserResponse toResponse(User user) {
        return new RegisterUserResponse(
                user.getPublicId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeleted()
        );
    }
}
