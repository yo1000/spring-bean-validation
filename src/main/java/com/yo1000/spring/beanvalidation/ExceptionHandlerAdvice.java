package com.yo1000.spring.beanvalidation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    // for RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleRequestBody(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");

        Map<String, Object> invalidProps = e.getFieldErrors().stream()
                .map(fieldError -> new AbstractMap.SimpleEntry<>(
                        fieldError.getField(),
                        fieldError.getRejectedValue()))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue));

        problemDetail.setProperty("properties", invalidProps);

        return problemDetail;
    }

    // for PathVariable, RequestParam, RequestHeader, and others
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleRequestParamAndPathVariableAndAOthers(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        Map<String, Object> invalidProps = e.getConstraintViolations().stream()
                .map(violation -> new AbstractMap.SimpleEntry<>(
                        violation.getPropertyPath().toString(),
                        violation.getInvalidValue()))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue));

        problemDetail.setProperty("properties", invalidProps);

        return problemDetail;
    }
}
