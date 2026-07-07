package com.likelion.collab_session.domain.record.repository;

import com.likelion.collab_session.domain.record.entity.Record;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

  // 식물 단건 조회
  Optional<Record> findByPlantIdAndRecordDate(Long plantId, LocalDate recordDate);

  // 식물 등록 여부 확인
  boolean existsByPlantIdAndRecordDate(Long plantId, LocalDate recordDate);

  // 식물 기록 갯수 조회
  long countByPlantId(Long plantId);

  // 마지막으로 물을 준 기록 조회
  Optional<Record> findTopByPlantIdAndWateredTrueOrderByRecordDateDesc(Long plantId);

  // (홈 화면 상단 표시) 성장 태그 존재하는 가장 최근 기록 하나 조회
  Optional<Record> findTopByPlantIdAndGrowthTagIsNotNullOrderByRecordDateDesc(Long plantId);

  // (타임라인 표시) 오래된순 기록 표시
  List<Record> findByPlantIdOrderByRecordDateAsc(Long plantId);

  List<Record> findAllByPlantIdInOrderByRecordDateAsc(List<Long> plantIds);
}
