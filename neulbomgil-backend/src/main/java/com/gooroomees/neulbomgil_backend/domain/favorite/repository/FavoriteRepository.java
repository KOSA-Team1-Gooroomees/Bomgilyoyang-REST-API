package com.gooroomees.neulbomgil_backend.domain.favorite.repository;

import com.gooroomees.neulbomgil_backend.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(Long userId);

    Optional<Favorite> findByUserIdAndFacilityId(Long userId, String facilityId);

    void deleteByUserIdAndFacilityId(Long userId, String facilityId);
}
