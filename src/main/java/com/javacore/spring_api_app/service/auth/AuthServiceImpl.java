package com.javacore.spring_api_app.service.auth;

import com.javacore.spring_api_app.domain.EmailNormalizer;
import com.javacore.spring_api_app.domain.NameNormalizer;
import com.javacore.spring_api_app.dto.request.LoginUserRequest;
import com.javacore.spring_api_app.dto.request.RegisterUserRequest;
import com.javacore.spring_api_app.dto.response.LoginUserResponse;
import com.javacore.spring_api_app.dto.response.RegisterUserResponse;
import com.javacore.spring_api_app.entity.User;
import com.javacore.spring_api_app.exception.custom.BusinessException;
import com.javacore.spring_api_app.repository.UserRepository;
import com.javacore.spring_api_app.service.token.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
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
                .password(passwordEncoder.encode(request.password()))
                .build();

        return toResponse(userRepository.save(user));
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            ));
        } catch (Exception e) {
            throw new BusinessException("Email ou senha incorreto");
        }

        User user = userRepository.findByEmailAndDeletedFalse(request.email())
                .orElseThrow(() -> new BusinessException("Email ou senha incorreto"));

        return new LoginUserResponse(tokenService.generateToken(user));
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
