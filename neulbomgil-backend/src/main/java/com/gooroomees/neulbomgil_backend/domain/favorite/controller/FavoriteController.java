package com.gooroomees.neulbomgil_backend.domain.favorite.controller;

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
    public ResponseEntity<Long> addFavorite(@RequestBody FavoriteRequest request) {
        return ResponseEntity.ok(favoriteService.saveFavorite(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(
            @PathVariable int userId,
            @Valid @ModelAttribute FavoriteSearchRequest request) {
        return ResponseEntity.ok(favoriteService.getUserFavoritesWithDetail(userId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam @RequestBody FavoriteRequest request) {
        favoriteService.deleteFavorite(request);
        return ResponseEntity.noContent().build();
    }
}