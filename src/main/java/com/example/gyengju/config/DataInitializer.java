package com.example.gyengju.config;

import com.example.gyengju.domain.spot.entity.Spot;
import com.example.gyengju.domain.spot.repository.SpotRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final SpotRepository spotRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${odii.api-key}")
    private String apiKey;

    @Override
    public void run(ApplicationArguments args) {
        if (spotRepository.count() > 0) {
            log.info("스팟 데이터가 이미 존재합니다. Odii API 호출 생략.");
            return;
        }

        log.info("Odii API에서 경주 스팟 데이터를 불러옵니다...");

        try {
            String url = UriComponentsBuilder
                    .fromUriString("https://apis.data.go.kr/B551011/Odii/storySearchList")
                    .queryParam("serviceKey", apiKey)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "GyeongjuGO")
                    .queryParam("keyword", "경주")
                    .queryParam("langCode", "ko")
                    .queryParam("numOfRows", "50")
                    .queryParam("_type", "json")
                    .build(false)
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode itemsNode = root.path("response").path("body").path("items").path("item");

            List<Spot> spots = new ArrayList<>();

            if (itemsNode.isArray()) {
                for (JsonNode item : itemsNode) {
                    Spot spot = parseSpot(item);
                    if (spot != null) spots.add(spot);
                }
            } else if (itemsNode.isObject()) {
                Spot spot = parseSpot(itemsNode);
                if (spot != null) spots.add(spot);
            }

            spotRepository.saveAll(spots);
            log.info("경주 스팟 {}개를 DB에 저장했습니다.", spots.size());

        } catch (Exception e) {
            log.error("Odii API 호출 실패: {}", e.getMessage());
        }
    }

    private Spot parseSpot(JsonNode item) {
        try {
            String title = item.path("title").asText("");
            String script = item.path("script").asText("");
            double mapY = item.path("mapY").asDouble(0);
            double mapX = item.path("mapX").asDouble(0);
            String stid = item.path("stid").asText("");
            String imageUrl = item.path("imageUrl").asText("");
            String audioUrl = item.path("audioUrl").asText("");

            // 좌표 없는 스팟은 제외
            if (mapX == 0 || mapY == 0) return null;

            return Spot.builder()
                    .stid(stid)
                    .nameKo(title)
                    .nameEn(title)
                    .descriptionKo(script)
                    .descriptionEn("")
                    .latitude(mapY)
                    .longitude(mapX)
                    .imageUrl(imageUrl)
                    .audioUrl(audioUrl)
                    .build();

        } catch (Exception e) {
            log.warn("스팟 파싱 실패: {}", e.getMessage());
            return null;
        }
    }
}
