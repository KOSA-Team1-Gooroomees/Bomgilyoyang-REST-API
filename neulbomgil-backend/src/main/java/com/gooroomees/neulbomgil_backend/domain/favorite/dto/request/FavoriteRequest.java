package com.gooroomees.neulbomgil_backend.domain.favorite.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteRequest {
    private int userId;
    private String facilityId;
}