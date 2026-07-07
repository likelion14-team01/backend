package com.likelion.collab_session.domain.photo.controller;

import com.likelion.collab_session.domain.photo.dto.PhotoResDto;
import com.likelion.collab_session.domain.photo.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="Photo", description = "사진 업로드 API")
@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

  private final PhotoService photoService;

  @Operation(summary = "사진 업로드")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PhotoResDto> uploadPhoto(@RequestParam("file") MultipartFile file){
    String photoUrl = photoService.store(file);
    return ResponseEntity.status(HttpStatus.CREATED).body(PhotoResDto.of(photoUrl));
  }

}
