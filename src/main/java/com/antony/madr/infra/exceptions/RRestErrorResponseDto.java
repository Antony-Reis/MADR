package com.antony.madr.infra.exceptions;

import org.springframework.http.HttpStatus;

public record RRestErrorResponseDto(HttpStatus status, String error) {
}
