package com.sideteam.groupsaver.domain.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RootController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /* IOException 시  로그만 찍어줌  */
    @NoLogging
    @ExceptionHandler(IOException.class)
    public void iOEException(Exception e, HttpServletRequest req, HttpServletResponse response) {
        logger.error("IOExceptionHandler** : " , e);

    }

    @NoLogging
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> paramViolationError(Exception e, HttpServletRequest req, HttpServletResponse response) throws IOException {
        logger.error("ExceptionHandler** : " , e);

        String exceptionType = req.getHeader("exceptionType");

        if(exceptionType==null || !exceptionType.equals("ajax")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        return new ResponseEntity<String>("작업 도중 문제가 생겨 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    // RequestBody validation 오류 처리
    @NoLogging
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<String> paramValidationError(MethodArgumentNotValidException ex, HttpServletRequest req, HttpServletResponse response) throws IOException {
        logger.error("@Validation ExceptionHandler## : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        errorLog(ex.getBindingResult());

        String exceptionType = req.getHeader("exceptionType");
        if (exceptionType == null || !exceptionType.equals("ajax")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /*
     * Controller 패키지 에 걸려있는 AOP 때문에
     * 오류 시 AOP 오류가 두번 남는 것을 방지
     * (특정 메소드 @NoLogging으로 제외)
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NoLogging {}

    public void errorLog(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }
        logger.info(builder.toString());
    }
}
