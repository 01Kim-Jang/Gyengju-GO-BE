package com.example.gyengju.domain.quest.controller;

import com.example.gyengju.domain.quest.dto.VisitRequest;
import com.example.gyengju.domain.quest.dto.VisitedSpotResponse;
import com.example.gyengju.domain.quest.service.QuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            @Valid @RequestBody VisitRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        questService.visitSpot(email, spotId, request);
        return ResponseEntity.ok("방문 완료!");
    }

    @GetMapping("/users/visited")
    public ResponseEntity<List<VisitedSpotResponse>> getVisitedSpots(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(questService.getVisitedSpots(email));
    }
}
