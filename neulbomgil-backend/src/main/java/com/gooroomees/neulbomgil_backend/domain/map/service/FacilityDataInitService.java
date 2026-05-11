package com.gooroomees.neulbomgil_backend.domain.map.service;

import com.gooroomees.neulbomgil_backend.domain.map.dto.response.VWorldResponse;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import com.gooroomees.neulbomgil_backend.domain.map.repository.FacilityRepository;
import com.gooroomees.neulbomgil_backend.domain.map.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityDataInitService {

    private final FacilityRepository facilityRepository;
    private final ParkRepository parkRepository;
    private final RestTemplate restTemplate;

    @Value("${vworld.api.key}")
    private String API_KEY;

    private final String API_URL = "https://api.vworld.kr/req/data";

    @Scheduled(cron = "0 0 4 * * *")
    public void refreshFacilities() {
        int page = 1;
        int size = 100;
        int totalPages = 1;

        log.info("VWorld 노인복지시설 데이터 갱신 시작...");

        try {
            while (page <= totalPages) {
                String url = UriComponentsBuilder.fromUriString(API_URL)
                        .queryParam("service", "data")
                        .queryParam("request", "GetFeature")
                        .queryParam("data", "LT_P_MGPRTFB")
                        .queryParam("key", API_KEY)
                        .queryParam("domain", "localhost:8080")
                        .queryParam("geomFilter", "BOX(0,0,200,200)")
                        .queryParam("size", size)
                        .queryParam("page", page)
                        .build().toUriString();

                VWorldResponse response = restTemplate.getForObject(url, VWorldResponse.class);
                if (response == null || response.getResponse() == null) break;

                VWorldResponse.ResponseData resBody = response.getResponse();

                // 첫 페이지에서 전체 페이지 수 계산
                if (page == 1 && resBody.getPage() != null) {
                    totalPages = Integer.parseInt(resBody.getPage().getTotal());
                    log.info("수집 대상: 총 {}페이지 ({}건)", totalPages, resBody.getRecord().getTotal());
                }

                // 데이터 추출 및 벌크 저장
                if (resBody.getResult() != null && resBody.getResult().getFeatureCollection() != null) {
                    List<VWorldResponse.Feature> features = resBody.getResult().getFeatureCollection().getFeatures();
                    if (features != null && !features.isEmpty()) {
                        savePageData(features);
                        log.info("진행: {}/{} 페이지 완료", page, totalPages);
                    }
                }

                page++;
                Thread.sleep(200); // Rate Limit 방지
            }
        } catch (Exception e) {
            log.error("수집 중 예외 발생 (Page: {}): ", page, e);
        }
        log.info("모든 데이터 갱신이 완료되었습니다.");
    }

    @Transactional
    public void savePageData(List<VWorldResponse.Feature> features) {
        // 1. 현재 페이지의 모든 ID 리스트 생성
        List<String> ids = features.stream().map(VWorldResponse.Feature::getId).toList();

        // 2. DB에서 기존 데이터 1번에 조회하여 Map으로 전환
        Map<String, Facility> existingMap = facilityRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Facility::getId, f -> f));

        // 3. 엔터티 변환 및 업데이트
        List<Facility> facilitiesToSave = features.stream()
                .map(feature -> mapToEntity(feature, Optional.ofNullable(existingMap.get(feature.getId()))))
                .toList();

        // 4. saveAll을 통한 벌크 저장
        facilityRepository.saveAll(facilitiesToSave);
    }

    private Facility mapToEntity(VWorldResponse.Feature feature, Optional<Facility> existing) {
        VWorldResponse.Properties props = feature.getProperties();
        List<Double> coords = feature.getGeometry().getCoordinates();
        double lon = coords.get(0);
        double lat = coords.get(1);

        return Facility.builder()
                .id(feature.getId())
                .facilityName(props.getFacilityName())
                .facilityTel(props.getFacilityTel())
                .categoryName(props.getCategoryName())
                .oldAddress(props.getOldAddress())
                .newAddress(props.getNewAddress())
                .longitude(lon)
                .latitude(lat)
                .capacityCnt(existing.map(Facility::getCapacityCnt).orElse(40))
                .currentCnt(existing.map(Facility::getCurrentCnt).orElse(0))
                .facilityImage(existing.map(Facility::getFacilityImage).orElse("facility.png"))
                .facilityScore(calculateFacilityScore(lat, lon))
                .build();
    }

    private Integer calculateFacilityScore(double lat, double lon) {
        Map<String, Object> stats = parkRepository.getParkStatsWithinRadius(lat, lon);
        long count = ((Number) stats.getOrDefault("count", 0)).longValue();
        double totalArea = stats.get("totalArea") != null ? ((Number) stats.get("totalArea")).doubleValue() : 0.0;

        double countScore = Math.min(count / 10.0, 1.0) * 2.5;
        double areaScore = Math.min(totalArea / 100000.0, 1.0) * 2.5;

        return Math.max((int) Math.round(countScore + areaScore), 1);
    }
}