package com.likelion.collab_session.domain.record.service;

import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.domain.plant.repository.PlantRepository;
import com.likelion.collab_session.domain.record.dto.RecordResDto;
import com.likelion.collab_session.domain.record.dto.RecordSaveReqDto;
import com.likelion.collab_session.domain.record.entity.GrowthTag;
import com.likelion.collab_session.domain.record.entity.Record;
import com.likelion.collab_session.domain.record.repository.RecordRepository;
import com.likelion.collab_session.global.exception.CustomException;
import com.likelion.collab_session.global.exception.GlobalErrorCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

  private final RecordRepository recordRepository;
  private final PlantRepository plantRepository;

  // 식물 기록 저장/수정
  @Transactional
  public RecordResDto saveRecord(Long plantId, LocalDate recordDate, RecordSaveReqDto req) {
    Plant plant = plantRepository.findById(plantId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.PLANT_NOT_FOUND));

    // 죽은 식물에는 이후 기록 저장/수정 요청을 모두 400 처리
    if (plant.isDead()) {
      throw new CustomException(GlobalErrorCode.DEAD_PLANT_RECORD_FORBIDDEN);
    }

    // 업서트 로직 수행
    Record record = recordRepository.findByPlantIdAndRecordDate(plantId, recordDate)
        .map(existing -> {
          existing.update(req.getWatered(), req.getPhotoUrl(), req.getNote(), req.getGrowthTag());
          return existing;
        })
        .orElseGet(() -> recordRepository.save(Record.builder()
            .plant(plant)
            .recordDate(recordDate)
            .watered(req.getWatered())
            .photoUrl(req.getPhotoUrl())
            .note(req.getNote())
            .growthTag(req.getGrowthTag())
            .build()));

    // growthTag=FAREWELL, 같은 트랜잭션에서 plants.died_at을 갱신
    if (req.getGrowthTag() == GrowthTag.FAREWELL) {
      plant.die(LocalDateTime.now());
    }

    return RecordResDto.of(record, plant.getCreatedAt().toLocalDate());
  }

  // 특정 날짜의 기록 조회
  public Optional<RecordResDto> getRecord(Long plantId, LocalDate recordDate) {
    Plant plant = plantRepository.findById(plantId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.PLANT_NOT_FOUND));

    return recordRepository.findByPlantIdAndRecordDate(plantId, recordDate)
        .map(record -> RecordResDto.of(record, plant.getCreatedAt().toLocalDate()));
  }
}
