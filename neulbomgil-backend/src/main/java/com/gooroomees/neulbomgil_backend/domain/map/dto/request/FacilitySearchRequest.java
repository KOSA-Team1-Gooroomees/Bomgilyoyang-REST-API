package com.gooroomees.neulbomgil_backend.domain.map.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilitySearchRequest {
    private String keyword;
    private Double userLat;
    private Double userLon;
    private String sort;
}

