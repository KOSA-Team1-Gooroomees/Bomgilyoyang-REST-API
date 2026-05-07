package com.gooroomees.neulbomgil_backend.domain.map.repository;

import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {

    @Query(value = "SELECT *, (6371 * acos(cos(radians(:lat)) * cos(radians(f.latitude)) " +
            "* cos(radians(f.longitude) - radians(:lon)) + sin(radians(:lat)) " +
            "* sin(radians(f.latitude)))) AS distance " +
            "FROM facility f " +
            "HAVING distance <= :radius " +
            "ORDER BY distance", nativeQuery = true)
    List<Facility> findFacilitiesWithinDistance(@Param("lat") Double lat,
                                                @Param("lon") Double lon,
                                                @Param("radius") Double radius);

    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:userLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:userLon)) " +
            "+ sin(radians(:userLat)) * sin(radians(latitude)))) AS distance " +
            "FROM facility f " +
            "WHERE f.old_address LIKE CONCAT('%', :keyword, '%') " +
            "   OR f.new_address LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY " +
            "CASE WHEN :sort = 'score' THEN f.facility_score END DESC, " +
            "CASE WHEN :sort = 'distance' THEN distance END ASC",
            nativeQuery = true)
    List<Facility> searchByRegion(
            @Param("keyword") String keyword,
            @Param("userLat") Double userLat,
            @Param("userLon") Double userLon,
            @Param("sort") String sort);
}
