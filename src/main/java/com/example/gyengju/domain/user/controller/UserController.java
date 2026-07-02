package com.example.gyengju.domain.user.controller;

import com.example.gyengju.domain.user.dto.SignupRequest;
import com.example.gyengju.domain.user.dto.UserResponse;
import com.example.gyengju.domain.user.service.UserService;
import com.example.gyengju.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저", description = "회원가입 및 유저 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody @Valid SignupRequest request) {
        Long userId = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 성공", userId));
    }

    @Operation(summary = "유저 조회", description = "유저 ID로 유저 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.findById(id)));
    }

    @Operation(summary = "언어 설정", description = "유저의 선호 언어를 설정합니다. (예: ko, en, ja, zh)")
    @PatchMapping("/{id}/language")
    public ResponseEntity<ApiResponse<Void>> updateLanguage(@PathVariable Long id,
                                                            @RequestParam String language) {
        userService.updateLanguage(id, language);
        return ResponseEntity.ok(ApiResponse.success("언어 설정 완료", null));
    }
}
