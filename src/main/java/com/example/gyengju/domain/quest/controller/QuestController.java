package com.example.gyengju.domain.quest.controller;

import com.example.gyengju.domain.quest.dto.VisitRequest;
import com.example.gyengju.domain.quest.dto.VisitedSpotResponse;
import com.example.gyengju.domain.quest.service.QuestService;
import com.example.gyengju.global.ApiResponse;
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
    public ResponseEntity<ApiResponse<Void>> visitSpot(
            @PathVariable Long spotId,
            @Valid @RequestBody VisitRequest request,
            Authentication authentication) {
        questService.visitSpot(authentication.getName(), spotId, request);
        return ResponseEntity.ok(ApiResponse.success("방문 완료!", null));
    }

    @GetMapping("/users/visited")
    public ResponseEntity<ApiResponse<List<VisitedSpotResponse>>> getVisitedSpots(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(questService.getVisitedSpots(authentication.getName())));
    }
}
