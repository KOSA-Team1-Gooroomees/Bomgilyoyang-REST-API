package com.gooroomees.neulbomgil_backend.domain.favorite.dto.response;

import com.gooroomees.neulbomgil_backend.domain.favorite.entity.Favorite;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Park;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FavoriteResponse {
    private final Long id;
    private final Long userId;
    private final String facilityId;
    private final Facility facility;
}