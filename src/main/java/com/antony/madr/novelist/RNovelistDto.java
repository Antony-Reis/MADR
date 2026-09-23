package com.antony.madr.novelist;

import jakarta.validation.constraints.NotBlank;

public record RNovelistDto(@NotBlank(message = "Novelist is required") String name) {
}
