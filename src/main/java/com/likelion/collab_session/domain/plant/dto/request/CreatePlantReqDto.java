package com.likelion.collab_session.domain.plant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "식물 등록 요청 DTO")
public class CreatePlantReqDto {

    @NotBlank(message = "식물 이름은 필수입니다.")
    @Size(max = 50, message = "식물 이름은 50자 이하여야 합니다.")
    @Schema(description = "식물 이름 (종 이름 자동 입력, '기타'만 직접 입력)", example = "몬스테라", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @NotNull(message = "종 ID는 필수입니다.")
    @Schema(description = "종 ID — GET /api/species에서 선택", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long speciesId;

    @NotNull(message = "급수 주기는 필수입니다.")
    @Min(value = 1, message = "급수 주기는 1 이상이어야 합니다.")
    @Schema(description = "급수 주기(일). 프리필 후 수정 가능", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer wateringIntervalDays;

    @Schema(description = "대표 사진 URL — POST /api/photos 반환값 (선택)", example = "/images/3f2a9c1e.jpg")
    private String photoUrl;
}
