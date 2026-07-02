package com.example.gyengju.domain.spot.controller;

import com.example.gyengju.domain.spot.dto.SpotResponse;
import com.example.gyengju.domain.spot.service.SpotService;
import com.example.gyengju.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유적지", description = "유적지 조회 API")
@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @Operation(summary = "전체 유적지 조회", description = "경주의 모든 유적지 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SpotResponse>>> getAllSpots() {
        return ResponseEntity.ok(ApiResponse.success(spotService.findAll()));
    }

    @Operation(summary = "유적지 단건 조회", description = "유적지 ID로 특정 유적지 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpotResponse>> getSpot(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(spotService.findById(id)));
    }
}
