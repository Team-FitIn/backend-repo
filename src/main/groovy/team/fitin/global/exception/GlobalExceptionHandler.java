package team.fitin.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException; // 추가
import team.fitin.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 단순 리소스 미발견 처리
     * 이 부분은 에러 로그를 남기지 않고 404 응답만 보냅니다.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * RuntimeException 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException 발생: ", e);

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("RUNTIME_ERROR")
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 진짜 심각한 에러만 로그로 남깁니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unknown Exception 발생: ", e);

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("SERVER_ERROR")
                .message("서버 내부에서 오류가 발생했습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
