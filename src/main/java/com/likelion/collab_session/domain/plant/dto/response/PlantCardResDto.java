package com.likelion.collab_session.domain.plant.dto.response;

import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.domain.record.entity.GrowthTag;
import com.likelion.collab_session.domain.species.entity.Species;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Schema(description = "식물 카드 응답 DTO (홈 목록·등록 응답 공용)")
public class PlantCardResDto {

    @Schema(description = "식물 ID", example = "1")
    private Long plantId;

    @Schema(description = "표시 이름 (종 이름 또는 기타의 직접 입력 이름)", example = "몬스테라")
    private String nickname;

    @Schema(description = "종 이름", example = "몬스테라")
    private String speciesName;

    @Schema(description = "종 카테고리 배지 (기타면 null → 미표시)", example = "관엽식물")
    private String category;

    @Schema(description = "대표 사진 — 있으면 카드 썸네일", example = "/images/3f2a9c1e.jpg")
    private String photoUrl;

    @Schema(description = "종 3D 일러스트 — photoUrl 없을 때 대체 표시", example = "/assets/monstera.png")
    private String speciesImageUrl;

    @Schema(description = "내 급수 주기 — 카드의 '💧7일마다'", example = "7")
    private int wateringIntervalDays;

    @Schema(description = "키운 지 며칠째 — 카드의 '22일째'", example = "22")
    private int daysSinceStart;

    @Schema(description = "누적 기록 수 — 타임라인 칩 '몬스테라(3)'용", example = "3")
    private int recordCount;

    @Schema(description = "마지막 물 준 날 (기록 없으면 null)", example = "2026-07-04")
    private LocalDate lastWateredDate;

    @Schema(description = "물주기까지 남은 일수. 0 이하 = 오늘 물 주는 날 (dead면 null)", example = "3")
    private Integer waterDday;

    @Schema(description = "최근 7일 기록 여부, 오래된 날부터", example = "[true, true, false, true, true, false, false]")
    private List<Boolean> streak;

    @Schema(description = "최근 마일스톤 태그 (없으면 null)", example = "NEW_LEAF")
    private GrowthTag recentTag;

    @Schema(description = "최근 마일스톤 날짜", example = "2026-07-03")
    private LocalDate recentTagDate;

    @Schema(description = "죽음 여부 — true면 추모 카드로", example = "false")
    private boolean dead;

    @Schema(description = "함께한 일수 — dead=true일 때만", example = "37")
    private Integer daysTogether;

    // ===== 프론트 호환용 별칭 필드 (기존 필드는 그대로 유지) =====

    @Schema(description = "(프론트 호환) nickname과 동일")
    public String getName() {
        return nickname;
    }

    @Schema(description = "(프론트 호환) 'N일째' 표시 텍스트", example = "22일째")
    public String getDayText() {
        return daysSinceStart + "일째";
    }

    @Schema(description = "(프론트 호환) 'N일마다' 표시 텍스트", example = "7일마다")
    public String getWaterCycleText() {
        return wateringIntervalDays + "일마다";
    }

    @Schema(description = "(프론트 호환) 물주기 안내 텍스트", example = "3일후 물주기")
    public String getNextWateringText() {
        if (dead || waterDday == null) return "";
        return waterDday <= 0 ? "오늘 물주기" : waterDday + "일후 물주기";
    }


    @Schema(description = "(프론트 호환) 기록 화면 이동 경로", example = "/record/1")
    public String getPath() {
        return "/record/" + plantId;
    }

    /** 등록 직후 응답용 — 기록이 아직 없으므로 초기값으로 채운다 */
    public static PlantCardResDto ofNew(Plant plant) {
        Species species = plant.getSpecies();
        return PlantCardResDto.builder()
                .plantId(plant.getId())
                .nickname(plant.getNickname())
                .speciesName(species.getName())
                .category(species.getCategory())
                .photoUrl(plant.getPhotoUrl())
                .speciesImageUrl(species.getImageUrl())
                .wateringIntervalDays(plant.getWateringIntervalDays())
                .daysSinceStart(1)
                .recordCount(0)
                .lastWateredDate(null)
                .waterDday(0)
                .streak(List.of(false, false, false, false, false, false, false))
                .recentTag(null)
                .recentTagDate(null)
                .dead(false)
                .daysTogether(null)
                .build();
    }
}