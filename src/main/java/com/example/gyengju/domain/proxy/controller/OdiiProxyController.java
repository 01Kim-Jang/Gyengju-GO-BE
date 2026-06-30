package com.example.gyengju.domain.proxy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/odii")
@RequiredArgsConstructor
public class OdiiProxyController {

    private final RestTemplate restTemplate;

    @Value("${odii.api-key}")
    private String apiKey;

    private static final String ODII_BASE_URL = "https://apis.data.go.kr";

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
