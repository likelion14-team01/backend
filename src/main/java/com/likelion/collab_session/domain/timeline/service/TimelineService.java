package com.likelion.collab_session.domain.timeline.service;

import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.domain.plant.repository.PlantRepository;
import com.likelion.collab_session.domain.record.dto.RecordResDto;
import com.likelion.collab_session.domain.record.entity.Record;
import com.likelion.collab_session.domain.record.repository.RecordRepository;
import com.likelion.collab_session.domain.timeline.dto.TimelineResDto;
import com.likelion.collab_session.global.exception.CustomException;
import com.likelion.collab_session.global.exception.GlobalErrorCode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimelineService {

  private final PlantRepository plantRepository;
  private final RecordRepository recordRepository;

  // 식물 기록 데이터 가져오기
  public TimelineResDto getTimeline(Long plantId) {
    Plant plant = plantRepository.findById(plantId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.PLANT_NOT_FOUND));

    LocalDate startDate = plant.getCreatedAt().toLocalDate();
    List<Record> records = recordRepository.findByPlantIdOrderByRecordDateAsc(plantId);
    List<RecordResDto> recordDtos = records.stream()
        .map(record -> RecordResDto.of(record, startDate))
        .toList();

    boolean dead = plant.isDead();
    TimelineResDto.Memorial memorial = null;
    // 식물 사망시, 추모 데이터 계산
    if (dead) {
      LocalDate diedDate = plant.getDiedAt().toLocalDate();
      int daysTogether = (int) ChronoUnit.DAYS.between(startDate, diedDate) + 1;
      int milestoneCount = (int) records.stream()
          .filter(record -> record.getGrowthTag() != null)
          .count();
      memorial = TimelineResDto.Memorial.of(startDate, diedDate, daysTogether, records.size(),
          milestoneCount);
    }

    return TimelineResDto.of(plant.getId(), plant.getNickname(), dead, recordDtos, memorial);
  }
}
