package com.example.gyengju.domain.proxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "외부 API - Odii", description = "한국관광공사 Odii 스토리 검색 프록시 API")
@RestController
@RequestMapping("/api/odii")
@RequiredArgsConstructor
public class OdiiProxyController {

    private final RestTemplate restTemplate;

    @Value("${odii.api-key}")
    private String apiKey;

    private static final String ODII_BASE_URL = "https://apis.data.go.kr";

    @Operation(summary = "Odii 스토리 검색", description = "키워드와 언어 코드로 관광 스토리를 검색합니다. (langCode: ko, en, ja, zh)")
    @GetMapping("/**")
    public ResponseEntity<String> proxyOdii(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "ko") String langCode,
            @RequestParam(defaultValue = "50") String numOfRows,
            @RequestParam(defaultValue = "ETC") String MobileOS,
            @RequestParam(defaultValue = "GyeongjuGO") String MobileApp) {

        String url = UriComponentsBuilder
                .fromUriString(ODII_BASE_URL + "/B551011/Odii/storySearchList")
                .queryParam("serviceKey", apiKey)
                .queryParam("MobileOS", MobileOS)
                .queryParam("MobileApp", MobileApp)
                .queryParam("keyword", keyword)
                .queryParam("langCode", langCode)
                .queryParam("numOfRows", numOfRows)
                .queryParam("_type", "json")
                .build(false)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}
