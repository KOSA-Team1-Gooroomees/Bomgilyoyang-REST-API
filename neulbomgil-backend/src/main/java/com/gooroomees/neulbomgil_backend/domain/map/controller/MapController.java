package com.gooroomees.neulbomgil_backend.domain.map.controller;

import com.gooroomees.neulbomgil_backend.domain.map.dto.request.FacilitySearchRequest;
import com.gooroomees.neulbomgil_backend.domain.map.dto.request.MarkerRequest;
import com.gooroomees.neulbomgil_backend.domain.map.dto.request.NearbyParkRequest;
import com.gooroomees.neulbomgil_backend.domain.map.dto.response.FacilityDetailResponse;
import com.gooroomees.neulbomgil_backend.domain.map.dto.response.FacilityMarkerResponse;
import com.gooroomees.neulbomgil_backend.domain.map.dto.response.FacilityResponse;
import com.gooroomees.neulbomgil_backend.domain.map.dto.response.NearParkResponse;
import com.gooroomees.neulbomgil_backend.domain.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/facilities/markers")
    public ResponseEntity<List<FacilityMarkerResponse>> getFacilityMarkers(@ModelAttribute MarkerRequest request) {
        return ResponseEntity.ok(mapService.getFacilityMarkers(request));
    }

    @GetMapping("/facilities")
    public ResponseEntity<List<FacilityResponse>> getFacilities(@ModelAttribute FacilitySearchRequest request) {
        return ResponseEntity.ok(mapService.getFacilities(request));
    }

    @GetMapping("/facilities/{facilityId}")
    public ResponseEntity<FacilityDetailResponse> getFacilityDetail(@PathVariable String facilityId) {
        try {
            FacilityDetailResponse response = mapService.getFacilityDetail(facilityId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/facilities/markers/{facilityId}/nearby-parks")
    public ResponseEntity<List<NearParkResponse>> getNearbyParks(
            @PathVariable String facilityId,
            @RequestParam(defaultValue = "3") Double radius) {

        NearbyParkRequest request = new NearbyParkRequest(facilityId, radius);
        return ResponseEntity.ok(mapService.getNearbyParks(request));
    }
}