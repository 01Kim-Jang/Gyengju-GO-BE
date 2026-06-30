package com.example.gyengju.domain.spot.controller;

import com.example.gyengju.domain.spot.dto.SpotResponse;
import com.example.gyengju.domain.spot.service.SpotService;
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
    public ResponseEntity<List<SpotResponse>> getAllSpots() {
        return ResponseEntity.ok(spotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpotResponse> getSpot(@PathVariable Long id) {
        return ResponseEntity.ok(spotService.findById(id));
    }
}
