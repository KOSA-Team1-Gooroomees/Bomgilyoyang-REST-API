package com.gooroomees.neulbomgil_backend.domain.map.repository;

import com.gooroomees.neulbomgil_backend.domain.map.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkRepository extends JpaRepository<Park, Integer> {

    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) " +
            "+ sin(radians(:lat)) * sin(radians(latitude)))) AS distance " +
            "FROM park " +
            "HAVING distance <= :radius " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<Park> findNearbyParks(@Param("lat") Double lat,
                               @Param("lon") Double lon,
                               @Param("radius") Double radius);
}
