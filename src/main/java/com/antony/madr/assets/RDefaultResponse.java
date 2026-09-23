package com.antony.madr.assets;

import org.springframework.boot.context.properties.bind.DefaultValue;

public record RDefaultResponse(@DefaultValue("Hello World!") String string) {
}
