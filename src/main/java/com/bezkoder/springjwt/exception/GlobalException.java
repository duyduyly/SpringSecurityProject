package com.bezkoder.springjwt.exception;

import com.bezkoder.springjwt.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bezkoder.springjwt.constant.Constant.FAILED;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NoSuchFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse noSuchException(HttpServletRequest request,
                                  HttpServletResponse response, Exception ex) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(FAILED)
                .error(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse resourceException(HttpServletRequest request,
                                    HttpServletResponse response, Exception ex) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(FAILED)
                .error(ex.getMessage())
                .build();
    }


}
