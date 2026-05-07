package com.gooroomees.neulbomgil_backend.domain.map.dto;

import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityMarkerDTO {

    private String id;
    private String facilityName;
    private String newAddress;
    private Double longitude;
    private Double latitude;

    @Builder
    public FacilityMarkerDTO(String id, String facilityName, String newAddress, Double longitude, Double latitude) {
        this.id = id;
        this.facilityName = facilityName;
        this.newAddress = newAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static FacilityMarkerDTO from(Facility facility) {
        return FacilityMarkerDTO.builder()
                .id(facility.getId())
                .facilityName(facility.getFacilityName())
                .newAddress(facility.getNewAddress())
                .longitude(facility.getLongitude())
                .latitude(facility.getLatitude())
                .build();
    }
}