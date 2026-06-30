package com.example.gyengju.domain.proxy.controller;

import com.example.gyengju.domain.proxy.dto.ChatRequest;
import com.example.gyengju.domain.proxy.dto.TranslateRequest;
import com.example.gyengju.domain.proxy.service.OpenAiProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proxy/ai")
@RequiredArgsConstructor
public class OpenAiProxyController {

    private final OpenAiProxyService openAiProxyService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(openAiProxyService.chat(request));
    }

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslateRequest request) {
        return ResponseEntity.ok(openAiProxyService.translate(request));
    }
}
