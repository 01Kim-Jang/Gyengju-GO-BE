package com.example.gyengju.domain.spot.service;

import com.example.gyengju.domain.spot.dto.SpotResponse;
import com.example.gyengju.domain.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {

    private final SpotRepository spotRepository;

    public List<SpotResponse> findAll() {
        return spotRepository.findAll().stream()
                .map(SpotResponse::from)
                .toList();
    }

    public SpotResponse findById(Long id) {
        return spotRepository.findById(id)
                .map(SpotResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스팟입니다."));
    }
}
