package com.gooroomees.neulbomgil_backend.domain.map.controller;

import com.gooroomees.neulbomgil_backend.domain.map.dto.FacilityMarkerDTO;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Park;
import com.gooroomees.neulbomgil_backend.domain.map.repository.FacilityRepository;
import com.gooroomees.neulbomgil_backend.domain.map.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final FacilityRepository facilityRepository;
    private final ParkRepository parkRepository;

    // 지도 시설 마커 조회 (지도에 마커 표시용)
    @GetMapping("/facilities/markers")
    public ResponseEntity<List<FacilityMarkerDTO>> getFacilityMarkers(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam(value = "radius", defaultValue = "10.0") Double radius) {
        List<FacilityMarkerDTO> facilityMarkers = facilityRepository
                .findFacilitiesWithinDistance(lat, lon, radius)
                .stream()
                .map(FacilityMarkerDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(facilityMarkers);
    }

    // 시설 목록 조회 (검색 및 정렬)
    @GetMapping("/facilities")
    public ResponseEntity<List<Facility>> getFacilities(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "37.3943") Double userLat,
            @RequestParam(defaultValue = "126.9443") Double userLon,
            @RequestParam(defaultValue = "score") String sort) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<Facility> facilities = facilityRepository.searchByRegion(
                keyword.trim(),
                userLat,
                userLon,
                sort
        );

        return ResponseEntity.ok(facilities);
    }

    // 특정 시설 상세 정보
    @GetMapping("/facilities/{facilityId}")
    public ResponseEntity<Facility> getFacilityDetail(@PathVariable String facilityId) {
        return facilityRepository.findById(facilityId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 특정 시설 일정 거리내에 있는 공원들 조회
    @GetMapping("/facilities/markers/{facilityId}/nearby-parks")
    public ResponseEntity<List<Park>> getNearbyParks(
            @PathVariable String facilityId,
            @RequestParam(defaultValue = "1000") Double radius) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 시설이 존재하지 않습니다."));

        Double radiusInKm = radius / 1000.0;

        List<Park> nearbyParks = parkRepository.findNearbyParks(
                facility.getLatitude(),
                facility.getLongitude(),
                radiusInKm
        );

        return ResponseEntity.ok(nearbyParks);
    }
}