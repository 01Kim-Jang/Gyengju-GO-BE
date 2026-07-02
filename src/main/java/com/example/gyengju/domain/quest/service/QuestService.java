package com.example.gyengju.domain.quest.service;

import com.example.gyengju.domain.quest.dto.VisitRequest;
import com.example.gyengju.domain.quest.dto.VisitedSpotResponse;
import com.example.gyengju.domain.quest.entity.UserSpot;
import com.example.gyengju.domain.quest.repository.UserSpotRepository;
import com.example.gyengju.domain.spot.entity.Spot;
import com.example.gyengju.domain.spot.repository.SpotRepository;
import com.example.gyengju.domain.user.entity.User;
import com.example.gyengju.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestService {

    private static final double MAX_DISTANCE_METERS = 50.0;

    private final UserSpotRepository userSpotRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;

    @Transactional
    public void visitSpot(String email, Long spotId, VisitRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new IllegalArgumentException("스팟을 찾을 수 없습니다."));

        if (userSpotRepository.existsByUserIdAndSpotId(user.getId(), spotId)) {
            throw new IllegalStateException("이미 방문한 스팟입니다.");
        }

        double distance = calculateDistance(
                request.getLatitude(), request.getLongitude(),
                spot.getLatitude(), spot.getLongitude()
        );

        if (distance > MAX_DISTANCE_METERS) {
            throw new IllegalArgumentException("스팟과 너무 멀리 있습니다. (현재 거리: " + (int) distance + "m)");
        }

        userSpotRepository.save(UserSpot.builder()
                .user(user)
                .spot(spot)
                .build());
    }

    @Transactional(readOnly = true)
    public List<VisitedSpotResponse> getVisitedSpots(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        return userSpotRepository.findByUserId(user.getId()).stream()
                .map(VisitedSpotResponse::from)
                .collect(Collectors.toList());
    }

    // Haversine 공식 - 두 좌표 사이 거리 계산 (미터)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
