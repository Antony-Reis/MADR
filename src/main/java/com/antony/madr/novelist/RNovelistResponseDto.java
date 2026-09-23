package com.antony.madr.novelist;

import com.antony.madr.book.RBookResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

public record RNovelistResponseDto(String name, Set<RBookResponseDto> books) {
    public RNovelistResponseDto(NovelistEntity entity){
        this(entity.getName(), entity.getBooks() != null ? entity.getBooks()
                .stream().map(RBookResponseDto::new).collect(Collectors.toSet()):Set.of());
    }
}
