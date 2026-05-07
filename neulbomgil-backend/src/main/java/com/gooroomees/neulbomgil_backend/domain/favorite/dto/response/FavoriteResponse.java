package com.gooroomees.neulbomgil_backend.domain.favorite.dto.response;

import com.gooroomees.neulbomgil_backend.domain.favorite.entity.Favorite;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FavoriteResponse {
    private final Long id;
    private final int userId;
    private final String facilityId;
    private Facility facility;
}