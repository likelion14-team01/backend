package com.likelion.collab_session.domain.species.dto;

import com.likelion.collab_session.domain.species.entity.Species;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpeciesResDto {

    @Schema(description = "종 ID", example = "1")
    private Long speciesId;

    @Schema(description = "종 이름", example = "몬스테라")
    private String name;

    @Schema(description = "카테고리 배지 (관엽식물·다육·선인장·허브). '기타'는 null → 배지 미표시", example = "관엽식물")
    private String category;

    @Schema(description = "한 줄 설명", example = "밝은 간접광에서 잘 자라요")
    private String description;

    @Schema(description = "주기 범위 표시용 텍스트", example = "5~7일")
    private String intervalText;

    @Schema(description = "종 3D 일러스트 (정적 에셋)", example = "/assets/monstera.png")
    private String imageUrl;

    @Schema(description = "계산·프리필용 대표 주기(일)", example = "7")
    private int wateringIntervalDays;

    public static SpeciesResDto from(Species species) {
        return SpeciesResDto.builder()
                .speciesId(species.getId())
                .name(species.getName())
                .category(species.getCategory())
                .description(species.getDescription())
                .intervalText(species.getIntervalText())
                .imageUrl(species.getImageUrl())
                .wateringIntervalDays(species.getWateringIntervalDays())
                .build();
    }
}