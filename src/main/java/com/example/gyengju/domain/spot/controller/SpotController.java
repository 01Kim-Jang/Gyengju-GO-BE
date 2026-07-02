package com.example.gyengju.domain.spot.controller;

import com.example.gyengju.domain.spot.dto.SpotResponse;
import com.example.gyengju.domain.spot.service.SpotService;
import com.example.gyengju.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpotResponse>>> getAllSpots() {
        return ResponseEntity.ok(ApiResponse.success(spotService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpotResponse>> getSpot(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(spotService.findById(id)));
    }
}
