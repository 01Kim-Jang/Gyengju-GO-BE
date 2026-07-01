package com.example.gyengju.domain.quest.repository;

import com.example.gyengju.domain.quest.entity.UserSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSpotRepository extends JpaRepository<UserSpot, Long> {

    List<UserSpot> findByUserId(Long userId);

    boolean existsByUserIdAndSpotId(Long userId, Long spotId);
}
