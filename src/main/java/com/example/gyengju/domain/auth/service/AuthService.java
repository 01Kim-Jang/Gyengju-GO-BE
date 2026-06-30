package com.example.gyengju.domain.auth.service;

import com.example.gyengju.config.JwtProvider;
import com.example.gyengju.domain.auth.dto.LoginRequest;
import com.example.gyengju.domain.auth.dto.LoginResponse;
import com.example.gyengju.domain.user.entity.User;
import com.example.gyengju.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.generateToken(user.getEmail());
        return new LoginResponse(token, user.getEmail(), user.getNickname());
    }
}
