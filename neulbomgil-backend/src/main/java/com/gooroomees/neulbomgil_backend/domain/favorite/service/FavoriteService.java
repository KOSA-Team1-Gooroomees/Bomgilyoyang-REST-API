package com.gooroomees.neulbomgil_backend.domain.favorite.service;

import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.response.FavoriteResponse;
import com.gooroomees.neulbomgil_backend.domain.favorite.entity.Favorite;
import com.gooroomees.neulbomgil_backend.domain.favorite.repository.FavoriteRepository;
import com.gooroomees.neulbomgil_backend.domain.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public Long saveFavorite(FavoriteRequest requestDto) {
        favoriteRepository.findByUserIdAndFacilityId(requestDto.getUserId(), requestDto.getFacilityId())
                .ifPresent(f -> {
                    throw new IllegalStateException("이미 즐겨찾기한 시설입니다.");
                });

        Favorite favorite = Favorite.builder()
                .userId(requestDto.getUserId())
                .facilityId(requestDto.getFacilityId())
                .build();

        return favoriteRepository.save(favorite).getId();
    }

    private final MapService mapService;

    public List<FavoriteResponse> getUserFavoritesWithDetail(int userId) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        return favorites.stream().map(favorite -> {
            try {
                var facilityDetail = mapService.getFacilityDetail(favorite.getFacilityId());

                return FavoriteResponse.builder()
                        .id(favorite.getId())
                        .userId(favorite.getUserId())
                        .facilityId(favorite.getFacilityId())
                        .facility(facilityDetail)
                        .build();
            } catch (IllegalArgumentException e) {
                return FavoriteResponse.builder()
                        .id(favorite.getId())
                        .userId(favorite.getUserId())
                        .facilityId(favorite.getFacilityId())
                        .facility(null)
                        .build();
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFavorite(int userId, String facilityId) {
        favoriteRepository.deleteByUserIdAndFacilityId(userId, facilityId);
    }
}