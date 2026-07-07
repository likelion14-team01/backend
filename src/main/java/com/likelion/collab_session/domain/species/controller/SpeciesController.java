package com.likelion.collab_session.domain.species.controller;

import com.likelion.collab_session.domain.species.dto.SpeciesResDto;
import com.likelion.collab_session.domain.species.service.SpeciesService;
import com.likelion.collab_session.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Species", description = "식물 종 API")
@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @Operation(summary = "종 목록 조회", description = "등록 폼 드롭다운용 종 목록을 반환합니다 ('기타' 포함). 종 선택 시 급수 주기 프리필에 사용됩니다.")
    @GetMapping
    public BaseResponse<List<SpeciesResDto>> getSpeciesList() {
        return BaseResponse.success(speciesService.getSpeciesList());
    }
}
