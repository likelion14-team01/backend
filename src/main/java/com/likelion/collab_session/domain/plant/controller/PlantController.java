package com.likelion.collab_session.domain.plant.controller;

import com.likelion.collab_session.domain.plant.dto.request.CreatePlantReqDto;
import com.likelion.collab_session.domain.plant.dto.response.PlantCardResDto;
import com.likelion.collab_session.domain.plant.service.PlantService;
import com.likelion.collab_session.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Plant", description = "식물 API")
@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @Operation(summary = "식물 등록", description = "새 식물을 등록합니다. 급수 주기는 종 선택 시 프리필된 값을 수정한 최종값, 대표 사진은 선택입니다.")
    @PostMapping
    public BaseResponse<PlantCardResDto> createPlant(@Valid @RequestBody CreatePlantReqDto reqDto) {
        return BaseResponse.success(201, "식물이 등록되었습니다.", plantService.createPlant(reqDto));
    }
}