package com.gooroomees.neulbomgil_backend.domain.favorite.service;

import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteSearchRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.response.FavoriteResponse;
import com.gooroomees.neulbomgil_backend.domain.favorite.entity.Favorite;
import com.gooroomees.neulbomgil_backend.domain.favorite.repository.FavoriteRepository;
import com.gooroomees.neulbomgil_backend.domain.map.dto.request.NearbyParkRequest;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Park;
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
    public Long saveFavorite(FavoriteRequest request) {
        favoriteRepository.findByUserIdAndFacilityId(request.getUserId(), request.getFacilityId())
                .ifPresent(f -> {
                    throw new IllegalStateException("이미 즐겨찾기한 시설입니다.");
                });

        Favorite favorite = Favorite.builder()
                .userId(request.getUserId())
                .facilityId(request.getFacilityId())
                .build();

        return favoriteRepository.save(favorite).getId();
    }

    private final MapService mapService;

    public List<FavoriteResponse> getUserFavoritesWithDetail(int userId, FavoriteSearchRequest request) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        return favorites.stream().map(favorite -> {
            try {
                var facilityDetail = mapService.getFacilityDetail(favorite.getFacilityId());
                List<Park> parks = mapService.getNearbyParks(new NearbyParkRequest(favorite.getFacilityId(), request.getRadius()));
                return FavoriteResponse.builder()
                        .id(favorite.getId())
                        .userId(favorite.getUserId())
                        .facilityId(favorite.getFacilityId())
                        .facility(facilityDetail)
                        .parks(parks)
                        .build();
            } catch (IllegalArgumentException e) {
                return FavoriteResponse.builder()
                        .id(favorite.getId())
                        .userId(favorite.getUserId())
                        .facilityId(favorite.getFacilityId())
                        .facility(null)
                        .parks(null)
                        .build();
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFavorite(FavoriteRequest request) {
        favoriteRepository.deleteByUserIdAndFacilityId(request.getUserId(), request.getFacilityId());
    }
}