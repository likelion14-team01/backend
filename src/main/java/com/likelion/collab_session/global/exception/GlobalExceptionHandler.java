package com.likelion.collab_session.global.exception;

import com.likelion.collab_session.global.common.BaseResponse;
import com.likelion.collab_session.global.exception.model.BaseErrorCode;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 커스텀 예외
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    BaseErrorCode errorCode = ex.getErrorCode();
    log.warn("CustomException 발생: {} - {}", errorCode.getCode(), errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatus())
        .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
  }

  // Validation 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<?>> handleValidationException(
      MethodArgumentNotValidException ex) {
    String errorMessages =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.joining(" / "));
    log.warn("Validation 오류 발생: {}", errorMessages);
    return ResponseEntity.badRequest().body(
        BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),
            GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()));
  }

  // 요청 본문 JSON 파싱 실패 (예: changedTag에 존재하지 않는 enum 값 전달)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    log.warn("HttpMessageNotReadableException 발생: {}", ex.getMessage());
    return ResponseEntity.badRequest().body(
        BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),
            GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()));
  }

  // multipart 요청에 필수 파트(file 등)가 아예 없을 때 (POST /api/photos 파일 누락)
  @ExceptionHandler(MissingServletRequestPartException.class)
  protected ResponseEntity<BaseResponse<?>> handleMissingServletRequestPartException(
      MissingServletRequestPartException ex) {
    log.warn("MissingServletRequestPartException 발생: {}", ex.getMessage());
    return ResponseEntity.status(GlobalErrorCode.FILE_REQUIRED.getStatus())
        .body(BaseResponse.error(GlobalErrorCode.FILE_REQUIRED.getCode(),
            GlobalErrorCode.FILE_REQUIRED.getMessage()));
  }

  // 지원하지 않는 HTTP Method 호출 시
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    log.warn("HttpRequestMethodNotSupportedException 발생: {}", ex.getMessage());
    return ResponseEntity.status(GlobalErrorCode.METHOD_NOT_ALLOWED.getStatus())
        .body(BaseResponse.error(GlobalErrorCode.METHOD_NOT_ALLOWED.getCode(),
            GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage()));
  }

  // 메서드 인자 타입이 일치하지 않을 때
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.warn("MethodArgumentTypeMismatchException 발생: {}", ex.getMessage());
    return ResponseEntity.badRequest().body(
        BaseResponse.error(GlobalErrorCode.TYPE_MISMATCH.getCode(),
            GlobalErrorCode.TYPE_MISMATCH.getMessage()));
  }

  // 예상치 못한 예외
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
    log.error("Server 오류 발생: ", ex);
    return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(BaseResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }
}
