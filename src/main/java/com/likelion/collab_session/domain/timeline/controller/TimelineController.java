package com.likelion.collab_session.domain.timeline.controller;

import com.likelion.collab_session.domain.timeline.dto.TimelineResDto;
import com.likelion.collab_session.domain.timeline.service.TimelineService;
import com.likelion.collab_session.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Timeline", description = "타임라인 조회 API")
@RestController
@RequestMapping("/api/plants/{plantId}/timeline")
@RequiredArgsConstructor
public class TimelineController {

  private final TimelineService timelineService;

  @Operation(summary = "타임라인 조회")
  @GetMapping
  public ResponseEntity<BaseResponse<TimelineResDto>> getTimeline(@PathVariable Long plantId) {
    TimelineResDto result = timelineService.getTimeline(plantId);
    return ResponseEntity.ok(BaseResponse.success("타임라인을 조회했습니다.", result));
  }
}
