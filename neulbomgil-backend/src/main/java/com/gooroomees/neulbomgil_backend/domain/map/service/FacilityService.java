package com.gooroomees.neulbomgil_backend.domain.map.service;

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

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final ParkRepository parkRepository;
    private final RestTemplate restTemplate = new RestTemplate();

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
                String url = UriComponentsBuilder.fromUri(URI.create(API_URL))
                        .queryParam("service", "data")
                        .queryParam("request", "GetFeature")
                        .queryParam("data", "LT_P_MGPRTFB")
                        .queryParam("key", API_KEY)
                        .queryParam("domain", "localhost:8080")
                        .queryParam("geomFilter", "BOX(0,0,200,200)")
                        .queryParam("size", size)
                        .queryParam("page", page)
                        .toUriString();

                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                Map<String, Object> resBody = (Map<String, Object>) response.get("response");

                if (page == 1) {
                    Map<String, Object> pageInfo = (Map<String, Object>) resBody.get("page");
                    if (pageInfo != null) {
                        totalPages = Integer.parseInt(pageInfo.get("total").toString());
                        log.info("총 수집 대상 페이지: {} (전체 데이터: {}건)", totalPages, ((Map) resBody.get("record")).get("total"));
                    }
                }

                Map<String, Object> result = (Map<String, Object>) resBody.get("result");
                if (result != null) {
                    Map<String, Object> featureCollection = (Map<String, Object>) result.get("featureCollection");
                    List<Map<String, Object>> features = (List<Map<String, Object>>) featureCollection.get("features");

                    if (features != null && !features.isEmpty()) {
                        savePageData(features);
                        log.info("진행: {}/{} 페이지 완료 ({}건)", page, totalPages, features.size());
                    }
                }

                page++;
                Thread.sleep(200);
            }

        } catch (Exception e) {
            log.error("수집 중 오류 발생 (Page: {}): ", page, e);
        }

        log.info("모든 데이터 갱신이 완료되었습니다.");
    }

    @Transactional
    public void savePageData(List<Map<String, Object>> features) {
        for (Map<String, Object> feature : features) {
            String id = (String) feature.get("id");

            Optional<Facility> existingFacility = facilityRepository.findById(id);

            Facility facility = mapToEntity(feature, existingFacility);

            facilityRepository.save(facility);
        }
    }

    private Facility mapToEntity(Map<String, Object> feature, Optional<Facility> existing) {
        Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
        Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
        List<Double> coords = (List<Double>) geometry.get("coordinates");

        // 기존 데이터가 있으면 기존 값을 쓰고, 없으면 기본값(40, 0, "facility.png") 사용
        Integer capacity = existing.map(Facility::getCapacityCnt).orElse(40);
        Integer current = existing.map(Facility::getCurrentCnt).orElse(0);
        String image = existing.map(Facility::getFacilityImage).orElse("facility.png");

        Integer score = calculateFacilityScore(feature);

        return Facility.builder()
                .id((String) feature.get("id"))
                .facilityName((String) properties.get("fac_nam"))
                .facilityTel((String) properties.get("fac_tel"))
                .categoryName((String) properties.get("cat_nam"))
                .oldAddress((String) properties.get("fac_o_add"))
                .newAddress((String) properties.get("fac_n_add"))
                .longitude(coords.get(0))
                .latitude(coords.get(1))
                .capacityCnt(capacity)
                .currentCnt(current)
                .facilityImage(image)
                .facilityScore(score)
                .build();
    }

    private Integer calculateFacilityScore(Map<String, Object> feature) {
        Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
        List<Double> coords = (List<Double>) geometry.get("coordinates");
        double lon = coords.get(0);
        double lat = coords.get(1);

        Map<String, Object> stats = parkRepository.getParkStatsWithinRadius(lat, lon);

        long count = ((Number) stats.get("count")).longValue();
        double totalArea = stats.get("totalArea") != null ? ((Number) stats.get("totalArea")).doubleValue() : 0.0;

        // 개수 점수 (0~2.5점)
        // 예: 3km 내 공원 10개 이상이면 만점
        double countScore = Math.min(count / 10.0, 1.0) * 2.5;

        // 면적 점수 (0~2.5점)
        // 예: 총 면적 100,000㎡(약 3만평) 이상이면 만점
        double areaScore = Math.min(totalArea / 100000.0, 1.0) * 2.5;

        // 4. 종합 점수 합산 및 반올림 (1~5점)
        int finalScore = (int) Math.round(countScore + areaScore);

        // 최소 1점 보장
        return Math.max(finalScore, 1);
    }
}