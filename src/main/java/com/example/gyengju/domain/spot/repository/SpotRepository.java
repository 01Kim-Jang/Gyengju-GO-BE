package com.example.gyengju.domain.spot.repository;

import com.example.gyengju.domain.spot.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
