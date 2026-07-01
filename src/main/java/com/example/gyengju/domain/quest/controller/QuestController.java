package com.example.gyengju.domain.quest.controller;

import com.example.gyengju.domain.quest.dto.VisitRequest;
import com.example.gyengju.domain.quest.dto.VisitedSpotResponse;
import com.example.gyengju.domain.quest.service.QuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @PostMapping("/spots/{spotId}/visit")
    public ResponseEntity<String> visitSpot(
            @PathVariable Long spotId,
            @RequestParam Long userId,
            @Valid @RequestBody VisitRequest request) {
        questService.visitSpot(userId, spotId, request);
        return ResponseEntity.ok("방문 완료!");
    }

    @GetMapping("/users/{userId}/visited")
    public ResponseEntity<List<VisitedSpotResponse>> getVisitedSpots(@PathVariable Long userId) {
        return ResponseEntity.ok(questService.getVisitedSpots(userId));
    }
}
