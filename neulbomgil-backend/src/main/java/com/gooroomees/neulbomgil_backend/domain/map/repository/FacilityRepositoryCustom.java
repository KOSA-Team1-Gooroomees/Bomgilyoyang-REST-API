package com.gooroomees.neulbomgil_backend.domain.map.repository;

import com.gooroomees.neulbomgil_backend.domain.map.dto.request.FacilitySearchRequest;
import com.gooroomees.neulbomgil_backend.domain.map.dto.response.FacilityResponse;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;

import java.util.List;

public interface FacilityRepositoryCustom {
    List<FacilityResponse> searchByRegionCursor(FacilitySearchRequest request);
}
