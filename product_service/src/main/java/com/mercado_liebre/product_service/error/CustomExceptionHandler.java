package com.mercado_liebre.product_service.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity handleResponseException(ResponseException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getError(),
                e.getHttpStatus()
        );
        return new ResponseEntity<>(apiException,e.getHttpStatus());
    }

}
