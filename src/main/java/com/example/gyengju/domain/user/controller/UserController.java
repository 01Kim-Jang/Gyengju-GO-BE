package com.example.gyengju.domain.user.controller;

import com.example.gyengju.domain.user.dto.SignupRequest;
import com.example.gyengju.domain.user.dto.UserResponse;
import com.example.gyengju.domain.user.service.UserService;
import com.example.gyengju.global.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody @Valid SignupRequest request) {
        Long userId = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 성공", userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.findById(id)));
    }

    @PatchMapping("/{id}/language")
    public ResponseEntity<ApiResponse<Void>> updateLanguage(@PathVariable Long id,
                                                            @RequestParam String language) {
        userService.updateLanguage(id, language);
        return ResponseEntity.ok(ApiResponse.success("언어 설정 완료", null));
    }
}
