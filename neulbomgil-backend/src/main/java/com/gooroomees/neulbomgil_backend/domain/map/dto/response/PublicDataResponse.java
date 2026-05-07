package com.gooroomees.neulbomgil_backend.domain.map.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicDataResponse {
    @JsonProperty("records")
    private List<ParkResponse> records;
}
