package com.gooroomees.neulbomgil_backend.domain.favorite.controller;

import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteDeleteRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.request.FavoriteSearchRequest;
import com.gooroomees.neulbomgil_backend.domain.favorite.dto.response.FavoriteResponse;
import com.gooroomees.neulbomgil_backend.domain.favorite.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Long> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        return ResponseEntity.ok(favoriteService.saveFavorite(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(
            @PathVariable int userId,
            @ModelAttribute FavoriteSearchRequest request) {
        return ResponseEntity.ok(favoriteService.getUserFavoritesWithDetail(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable int userId,
            @Valid @RequestBody FavoriteDeleteRequest request) {
        favoriteService.deleteFavorite(userId, request);
        return ResponseEntity.noContent().build();
    }
}