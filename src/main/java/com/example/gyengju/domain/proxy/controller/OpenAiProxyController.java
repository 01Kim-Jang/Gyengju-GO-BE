package com.example.gyengju.domain.proxy.controller;

import com.example.gyengju.domain.proxy.dto.ChatRequest;
import com.example.gyengju.domain.proxy.dto.TranslateRequest;
import com.example.gyengju.domain.proxy.service.OpenAiProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "외부 API - OpenAI", description = "OpenAI 챗봇 및 번역 프록시 API")
@RestController
@RequestMapping("/api/proxy/ai")
@RequiredArgsConstructor
public class OpenAiProxyController {

    private final OpenAiProxyService openAiProxyService;

    @Operation(summary = "AI 챗봇", description = "OpenAI를 이용한 경주 관광 AI 챗봇입니다.")
    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(openAiProxyService.chat(request));
    }

    @Operation(summary = "AI 번역", description = "OpenAI를 이용한 텍스트 번역입니다.")
    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslateRequest request) {
        return ResponseEntity.ok(openAiProxyService.translate(request));
    }
}
