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

    // 커서 기반 페이지네이션을 위한 필드
    private String lastId;      // 마지막 시설의 ID
    private Double lastValue;   // 마지막 시설의 기준 값 (거리 혹은 점수)
    private Integer size = 10;  // 한 번에 가져올 데이터 수
}

