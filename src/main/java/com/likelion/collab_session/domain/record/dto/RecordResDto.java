package com.likelion.collab_session.domain.record.dto;

import com.likelion.collab_session.domain.record.entity.GrowthTag;
import com.likelion.collab_session.domain.record.entity.Record;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
@Schema(title = "RecordResDto", description = "기록 응답 DTO")
public class RecordResDto {

  @Schema(description = "기록 날짜")
  private final LocalDate recordDate;

  @Schema(description = "Day N (record_date - plants.created_at + 1)")
  private final int dayNumber;

  @Schema(description = "물 줌 여부")
  private final boolean watered;

  @Schema(description = "사진 URL")
  private final String photoUrl;

  @Schema(description = "한 줄 메모")
  private final String note;

  @Schema(description = "성장 태그")
  private final GrowthTag growthTag;

  // ===== 프론트 호환용 별칭 필드 =====

  @Schema(description = "(프론트 호환) 소문자 태그 — sprout/leaf/bloom/farewell (태그 없으면 null)")
  public String getTag() {
    return growthTag == null ? null : growthTag.toFrontTag();
  }

  @Schema(description = "(프론트 호환) 'M월 d일' 표시 텍스트", example = "6월 14일")
  public String getDate() {
    return recordDate == null ? null
            : recordDate.format(java.time.format.DateTimeFormatter.ofPattern("M월 d일"));
  }

  private RecordResDto(LocalDate recordDate, int dayNumber, boolean watered, String photoUrl,
      String note, GrowthTag growthTag) {
    this.recordDate = recordDate;
    this.dayNumber = dayNumber;
    this.watered = watered;
    this.photoUrl = photoUrl;
    this.note = note;
    this.growthTag = growthTag;
  }

  public static RecordResDto of(Record record, LocalDate plantCreatedDate) {
    int dayNumber = (int) ChronoUnit.DAYS.between(plantCreatedDate, record.getRecordDate()) + 1;
    return new RecordResDto(record.getRecordDate(), dayNumber, record.isWatered(),
        record.getPhotoUrl(), record.getNote(), record.getGrowthTag());
  }
}
