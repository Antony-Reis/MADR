package com.antony.madr.book;

import jakarta.validation.constraints.NotNull;

public record RBookDto(@NotNull Integer novelistId, @NotNull String title, @NotNull Integer year) {
}
