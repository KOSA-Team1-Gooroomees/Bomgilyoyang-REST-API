package com.gooroomees.neulbomgil_backend.domain.map.service;

import com.gooroomees.neulbomgil_backend.domain.map.entity.Facility;
import com.gooroomees.neulbomgil_backend.domain.map.repository.FacilityRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
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
        List<Facility> facilityList = features.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        facilityRepository.saveAll(facilityList);
    }

    private Facility mapToEntity(Map<String, Object> feature) {
        Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
        Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
        List<Double> coords = (List<Double>) geometry.get("coordinates");

        return Facility.builder()
                .id((String) feature.get("id"))
                .facilityName((String) properties.get("fac_nam"))
                .facilityTel((String) properties.get("fac_tel"))
                .categoryName((String) properties.get("cat_nam"))
                .oldAddress((String) properties.get("fac_o_add"))
                .newAddress((String) properties.get("fac_n_add"))
                .longitude(coords.get(0))
                .latitude(coords.get(1))
                .build();
    }
}