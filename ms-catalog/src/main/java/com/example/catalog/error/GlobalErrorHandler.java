package com.example.catalog.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.v;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler{

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleNotFound(ProductNotFoundException e, WebRequest request) {
        addWarnLog(HttpStatus.NOT_FOUND,
                "404",
                e.getMessage(),
                "NotFoundException",
                request.getContextPath());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Product Not Found");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    ProblemDetail handleAlreadyExists(ProductAlreadyExistsException e, WebRequest request) {
        addWarnLog(HttpStatus.BAD_REQUEST,
                "400",
                e.getMessage(),
                "ProductAlreadyExistsException",
                request.getContextPath());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problem.setTitle("Product Already Exists");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException e, WebRequest request) {
        addWarnLog(HttpStatus.BAD_REQUEST,
                "400",
                e.getMessage(),
                "MethodArgumentNotValidException",
                request.getContextPath());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation Error");
        problem.setDetail("One or more fields are invalid");

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e, WebRequest request) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR,
                "500",
                e.getMessage(),
                e,
                request.getContextPath());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error");
        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorCode, String errorMessage, Throwable ex, String requestUri) {
        log.error("[Error] | HttpStatus: {} | Code: {} | Type: {} | Path: {} | Message: {}",
                v("ERROR_HTTP_STATUS", httpStatus), errorCode, ex.getClass().getTypeName(),
                v("URI", requestUri), errorMessage, ex);
    }

    protected void addWarnLog(HttpStatus httpStatus, String errorCode, String errorMessage, String exceptionType, String requestUri) {
        log.warn("[Warn] | HttpStatus: {} | Code: {} | Type: {} | Path: {} | Message: {}",
                v("ERROR_HTTP_STATUS", httpStatus), errorCode, exceptionType, v("URI", requestUri),
                errorMessage);
    }
}
