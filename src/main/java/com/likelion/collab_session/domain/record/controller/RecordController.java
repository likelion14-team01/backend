package com.likelion.collab_session.domain.record.controller;

import com.likelion.collab_session.domain.record.dto.RecordResDto;
import com.likelion.collab_session.domain.record.dto.RecordSaveReqDto;
import com.likelion.collab_session.domain.record.service.RecordService;
import com.likelion.collab_session.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Record", description = "기록 저장/조회 API")
@RestController
@RequestMapping("/api/plants/{plantId}/records/{recordDate}")
@RequiredArgsConstructor
public class RecordController {

  private final RecordService recordService;

  @Operation(summary = "기록 저장/수정 (upsert)")
  @PutMapping
  public ResponseEntity<BaseResponse<RecordResDto>> saveRecord(
      @PathVariable Long plantId,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate,
      @Valid @RequestBody RecordSaveReqDto req) {
    RecordResDto result = recordService.saveRecord(plantId, recordDate, req);
    return ResponseEntity.ok(BaseResponse.success("기록이 저장되었습니다.", result));
  }

  @Operation(summary = "기록 조회")
  @ApiResponse(responseCode = "204", description = "해당 날짜 기록 없음 (에러 아님, 빈 폼 처리)")
  @GetMapping
  public ResponseEntity<BaseResponse<RecordResDto>> getRecord(
      @PathVariable Long plantId,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate) {
    Optional<RecordResDto> result = recordService.getRecord(plantId, recordDate);

    if (result.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(BaseResponse.success("기록을 조회했습니다.", result.get()));
  }
}
