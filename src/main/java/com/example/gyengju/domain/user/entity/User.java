package com.example.gyengju.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String language;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public User(String email, String password, String nickname, String language) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.language = language;
    }

    public void updateLanguage(String language) {
        this.language = language;
    }
}
