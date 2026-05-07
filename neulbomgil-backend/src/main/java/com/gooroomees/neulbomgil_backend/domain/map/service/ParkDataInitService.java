package com.gooroomees.neulbomgil_backend.domain.map.service;

import com.gooroomees.neulbomgil_backend.domain.map.dto.ParkJsonDto;
import com.gooroomees.neulbomgil_backend.domain.map.dto.PublicDataResponse;
import com.gooroomees.neulbomgil_backend.domain.map.entity.Park;
import com.gooroomees.neulbomgil_backend.domain.map.repository.ParkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkDataInitService {

    private final ParkRepository parkRepository;
    private final ResourceLoader resourceLoader;

    @Transactional
    public void initParkData() {
        try {
            parkRepository.deleteAllInBatch();

            Resource resource = resourceLoader.getResource("classpath:data/parks.json");
            InputStream inputStream = resource.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            PublicDataResponse response = mapper.readValue(inputStream, PublicDataResponse.class);
            List<ParkJsonDto> dtos = response.getRecords();

            List<Park> parks = dtos.stream()
                    .map(ParkJsonDto::toEntity)
                    .collect(Collectors.toList());

            parkRepository.saveAll(parks);
        } catch (IOException e) {
            throw new RuntimeException("공원 데이터 초기화 중 오류 발생", e);
        }
    }
}
