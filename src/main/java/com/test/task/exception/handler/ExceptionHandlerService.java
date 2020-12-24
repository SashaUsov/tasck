package com.test.task.exception.handler;

import com.test.task.exception.BusinessExceptions;
import com.test.task.domein.ex.ApiError;
import com.test.task.domein.ex.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerService {

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            BusinessExceptions.class,
            Exception.class
    })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        if (ex instanceof MethodArgumentNotValidException) {
            var status = HttpStatus.BAD_REQUEST;
            var manve = (MethodArgumentNotValidException) ex;

            return handleMethodArgumentNotValidException(manve, status);
        } else if (ex instanceof HttpMessageNotReadableException) {
            var status = HttpStatus.BAD_REQUEST;
            var mbre = (HttpMessageNotReadableException) ex;

            return handleHttpMessageNotReadableException(mbre, status);
        } else if (ex instanceof BusinessExceptions) {
            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            var be = (BusinessExceptions) ex;

            return handleBusinessExceptions(be, status);
        } else {
            var status = HttpStatus.INTERNAL_SERVER_ERROR;

            return handleExceptionInternal(ex, status, request);
        }
    }

    private ResponseEntity<ApiError> handleBusinessExceptions(BusinessExceptions ex, HttpStatus status) {

        return new ResponseEntity<>(new ApiError("business_exceptions",
                Collections.singleton(new ErrorInfo(status.toString(), ex.getMessage()))), status);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpStatus status) {
        var errorMessages = ex.getAllErrors().stream().map(x -> new ErrorInfo(x.getCode(), x.getDefaultMessage()));
        return new ResponseEntity<>(new ApiError("method_argument_not_valid_exception",
                errorMessages.collect(Collectors.toSet())), status);
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpStatus status) {
        var errorMessages = "Please use the numerical value of id";
        return new ResponseEntity<>(new ApiError("validation_exception",
                Collections.singleton(new ErrorInfo(status.toString(), errorMessages))), status);
    }

    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, HttpStatus status, WebRequest request
    ) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(new ApiError("unexpected_exception",
                Collections.singleton(new ErrorInfo(status.toString(), ex.getMessage()))), status);
    }
}
