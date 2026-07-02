package com.example.gyengju.domain.quest.controller;

import com.example.gyengju.domain.quest.dto.VisitRequest;
import com.example.gyengju.domain.quest.dto.VisitedSpotResponse;
import com.example.gyengju.domain.quest.service.QuestService;
import com.example.gyengju.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "퀘스트", description = "유적지 방문 및 방문 기록 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @Operation(summary = "유적지 방문 완료", description = "현재 위치(위도/경도)를 전송하여 유적지 방문을 완료합니다. 유적지와 50m 이내여야 합니다.")
    @PostMapping("/spots/{spotId}/visit")
    public ResponseEntity<ApiResponse<Void>> visitSpot(
            @PathVariable Long spotId,
            @Valid @RequestBody VisitRequest request,
            Authentication authentication) {
        questService.visitSpot(authentication.getName(), spotId, request);
        return ResponseEntity.ok(ApiResponse.success("방문 완료!", null));
    }

    @Operation(summary = "방문 목록 조회", description = "로그인한 유저의 방문 완료된 유적지 목록을 조회합니다.")
    @GetMapping("/users/visited")
    public ResponseEntity<ApiResponse<List<VisitedSpotResponse>>> getVisitedSpots(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(questService.getVisitedSpots(authentication.getName())));
    }
}
