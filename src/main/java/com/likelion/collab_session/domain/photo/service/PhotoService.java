package com.likelion.collab_session.domain.photo.service;

import com.likelion.collab_session.global.exception.CustomException;
import com.likelion.collab_session.global.exception.GlobalErrorCode;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {

  @Value("${file.upload-dir}")
  private String uploadDir;

  public String store(MultipartFile file) {
    validate(file);

    // 확장자 추출(.jpg, .png)
    String extension = extractExtension(file.getOriginalFilename());
    // 랜덤한 문자열(UUID) 고유 이름
    String savedFilename = UUID.randomUUID().toString().replace("-", "").substring(0, 8) + extension;

    try {
      // 디렉터리 생성, 파일 저장
      Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
      Files.createDirectories(dir);
      file.transferTo(dir.resolve(savedFilename));
    } catch (IOException e) {
      throw new UncheckedIOException("사진 저장에 실패했습니다.", e);
    }

    return "/images/" + savedFilename;
  }

  // 유효성 검사
  private void validate(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new CustomException(GlobalErrorCode.FILE_REQUIRED);
    }
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      throw new CustomException(GlobalErrorCode.INVALID_IMAGE_TYPE);
    }
  }

  // 확장자 추출
  private String extractExtension(String originalFilename) {
    if (originalFilename == null || !originalFilename.contains(".")) {
      return "";
    }
    return originalFilename.substring(originalFilename.lastIndexOf('.'));
  }
}
