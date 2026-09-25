package com.antony.madr.utils;

import org.springframework.boot.context.properties.bind.DefaultValue;

public record RDefaultResponse(@DefaultValue("Hello World!") String string) {
}
