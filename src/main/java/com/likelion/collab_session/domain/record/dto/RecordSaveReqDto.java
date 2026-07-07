package com.likelion.collab_session.domain.record.dto;

import com.likelion.collab_session.domain.record.entity.GrowthTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "RecordSaveReqDto", description = "기록 저장(upsert) 요청 DTO")
public class RecordSaveReqDto {

  @NotNull(message = "watered는 필수입니다.")
  @Schema(description = "true=줬어요 / false=패스", example = "true")
  private Boolean watered;

  @Schema(description = "사진 URL. null=사진 제거", example = "/images/3f2a9c1e.jpg")
  private String photoUrl;

  @Size(max = 100, message = "메모는 최대 100자입니다.")
  @Schema(description = "한 줄 메모, 최대 100자")
  private String note;

  @Schema(description = "성장 태그 (하루 1개, 단일 선택). FAREWELL 저장 시 식물이 죽음 처리")
  private GrowthTag growthTag;
}
