package com.likelion.collab_session.domain.photo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title="PhotoResDto", description = "사진 업로드 DTO")
public class PhotoResDto {

  @Schema(description = "업로드된 사진 URL", example = "/images/3f2a9c1e.jpg")
  private final String photoUrl;

  private PhotoResDto(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public static PhotoResDto of(String photoUrl) {
    return new PhotoResDto(photoUrl);
  }

}
