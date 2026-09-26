package com.antony.madr.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RRestErrorResponseDto> authErrorException(AuthenticationException ex){
        RRestErrorResponseDto response = new RRestErrorResponseDto(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RRestErrorResponseDto> forbiddenErrorException(AccessDeniedException ex){
        RRestErrorResponseDto response = new RRestErrorResponseDto(HttpStatus.FORBIDDEN, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RRestErrorResponseDto> notFoundErrorException(NotFoundException ex){
        RRestErrorResponseDto response = new RRestErrorResponseDto(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<RRestErrorResponseDto> conflictErrorException(ConflictException ex){
        RRestErrorResponseDto response = new RRestErrorResponseDto(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
