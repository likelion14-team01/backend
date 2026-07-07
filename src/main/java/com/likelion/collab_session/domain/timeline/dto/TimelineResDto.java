package com.likelion.collab_session.domain.timeline.dto;

import com.likelion.collab_session.domain.record.dto.RecordResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(title = "TimelineResDto", description = "타임라인 조회 응답 DTO")
public class TimelineResDto {

  @Schema(description = "식물 ID")
  private final Long plantId;

  @Schema(description = "표시 이름")
  private final String nickname;

  @Schema(description = "true면 추모 헤더로 전환")
  private final boolean dead;

  @Schema(description = "전체 기록, 날짜 오름차순. 없으면 빈 배열")
  private final List<RecordResDto> records;

  @Schema(description = "dead=false면 null")
  private final Memorial memorial;

  private TimelineResDto(Long plantId, String nickname, boolean dead, List<RecordResDto> records,
      Memorial memorial) {
    this.plantId = plantId;
    this.nickname = nickname;
    this.dead = dead;
    this.records = records;
    this.memorial = memorial;
  }

  public static TimelineResDto of(Long plantId, String nickname, boolean dead,
      List<RecordResDto> records, Memorial memorial) {
    return new TimelineResDto(plantId, nickname, dead, records, memorial);
  }

  @Getter
  @Schema(title = "Memorial", description = "추모 정보 (dead=true일 때만 존재)")
  public static class Memorial {

    @Schema(description = "키우기 시작한 날")
    private final LocalDate startedAt;

    @Schema(description = "죽은 날")
    private final LocalDate diedAt;

    @Schema(description = "함께한 일수")
    private final int daysTogether;

    @Schema(description = "전체 기록 수")
    private final int recordCount;

    @Schema(description = "마일스톤 수 (growthTag != null인 기록 수)")
    private final int milestoneCount;

    private Memorial(LocalDate startedAt, LocalDate diedAt, int daysTogether, int recordCount,
        int milestoneCount) {
      this.startedAt = startedAt;
      this.diedAt = diedAt;
      this.daysTogether = daysTogether;
      this.recordCount = recordCount;
      this.milestoneCount = milestoneCount;
    }

    public static Memorial of(LocalDate startedAt, LocalDate diedAt, int daysTogether,
        int recordCount, int milestoneCount) {
      return new Memorial(startedAt, diedAt, daysTogether, recordCount, milestoneCount);
    }
  }
}
