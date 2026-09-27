package com.antony.madr.utils;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;

public record RDefaultResponse(HttpStatus staus, @DefaultValue("Operation completed successfully") String string) {
}
