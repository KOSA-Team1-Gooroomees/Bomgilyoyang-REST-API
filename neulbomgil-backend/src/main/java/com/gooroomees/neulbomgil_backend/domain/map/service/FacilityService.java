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

    @Scheduled(cron = "0 0 4 * * *") // 매일 새벽 4시 실행
    @Transactional
    public void refreshFacilities() {
        int page = 1;
        int size = 100;

        while (true) {
            try {
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
                if ("ERROR".equals(resBody.get("status"))) {
                    log.info("모든 데이터를 수집했거나 오류 발생: {}", resBody.get("error"));
                    break;
                }

                Map<String, Object> result = (Map<String, Object>) resBody.get("result");
                Map<String, Object> featureCollection = (Map<String, Object>) result.get("featureCollection");
                List<Map<String, Object>> features = (List<Map<String, Object>>) featureCollection.get("features");

                if (features == null || features.isEmpty()) break;

                List<Facility> facilityList = features.stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList());

                facilityRepository.saveAll(facilityList);

                log.info("VWorld 데이터 페이지 {} 완료 ({}건)", page, facilityList.size());
                page++;

                Thread.sleep(200);

            } catch (Exception e) {
                log.error("데이터 갱신 중 예외 발생: ", e);
                break;
            }
        }
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