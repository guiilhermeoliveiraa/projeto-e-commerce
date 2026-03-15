package com.javacore.spring_api_app.entity.email;

import com.javacore.spring_api_app.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "email_validations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "expiresAt")
    private Instant expiresAt;

    @Builder.Default
    private Boolean used = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}