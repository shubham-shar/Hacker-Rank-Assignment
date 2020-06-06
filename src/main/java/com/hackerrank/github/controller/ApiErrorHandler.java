package com.hackerrank.github.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Null;
import java.io.IOException;

/**
 * @author Shubham Sharma
 * @date 6/6/20
 */
@Slf4j
@RestControllerAdvice
public class ApiErrorHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class })
    public ErrorResponse handleBadRequest(Throwable throwable) {
        log.error("Bad Request, Something went wrong", throwable.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ NullPointerException.class })
    public ErrorResponse handleNotFound(Throwable throwable) {
        log.error("null pointer exception, {}", throwable.getMessage(), throwable);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ IOException.class })
    protected ErrorResponse ioException(Throwable throwable) {
        log.error("Some IO exception occured {}", throwable.getMessage(), throwable);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @ExceptionHandler({ Exception.class})
    public ErrorResponse exception(Throwable throwable){
        log.error("Something went wrong {}", throwable.getMessage(), throwable);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @Data
    @AllArgsConstructor
    private class ErrorResponse{
        private HttpStatus statusCode;
        private String  message;
    }

}
