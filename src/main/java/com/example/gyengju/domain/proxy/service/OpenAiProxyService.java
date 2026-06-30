package com.example.gyengju.domain.proxy.service;

import com.example.gyengju.domain.proxy.dto.ChatRequest;
import com.example.gyengju.domain.proxy.dto.TranslateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiProxyService {

    private final RestTemplate restTemplate;

    @Value("${openai.api-key}")
    private String apiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    private static final String SYSTEM_PROMPT = """
            당신은 신라시대의 수도 경주를 안내하는 다국어 AI 여행 가이드(동료)입니다.
            여행객이 묻는 질문에 친절하고 흥미롭게 답해주세요. 가급적 역사적 사실에 기반하되,
            너무 딱딱하지 않은 어조로 이야기해주세요.
            포켓몬스터의 가이드 캐릭터처럼 밝고 에너지가 넘치게 말해주세요.
            """;

    private static final Map<String, String> LANG_MAP = Map.of(
            "ko", "Korean",
            "en", "English",
            "ja", "Japanese",
            "zh-chs", "Simplified Chinese",
            "vi", "Vietnamese",
            "th", "Thai",
            "fr", "French"
    );

    public String chat(ChatRequest request) {
        var systemMessage = Map.of("role", "system", "content", SYSTEM_PROMPT);
        var allMessages = new java.util.ArrayList<Map<String, String>>();
        allMessages.add(systemMessage);
        request.getMessages().forEach(m ->
                allMessages.add(Map.of("role", m.getRole(), "content", m.getContent())));

        return callOpenAi(allMessages, 0.7);
    }

    public String translate(TranslateRequest request) {
        String targetLanguage = LANG_MAP.getOrDefault(request.getTargetLangCode(), request.getTargetLangCode());
        String systemMsg = "Translate the following text to " + targetLanguage +
                ". Provide ONLY the translated text, without any additional comments.";

        var messages = List.of(
                Map.of("role", "system", "content", systemMsg),
                Map.of("role", "user", "content", request.getText())
        );

        return callOpenAi(messages, 0.3);
    }

    private String callOpenAi(Object messages, double temperature) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", messages,
                "temperature", temperature
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_URL, entity, String.class);
        return response.getBody();
    }
}
