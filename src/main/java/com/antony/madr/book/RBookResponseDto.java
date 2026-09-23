package com.antony.madr.book;

public record RBookResponseDto(String title, Integer year, Integer novelistId) {
    public RBookResponseDto(BookEntity bookDto){
        this(bookDto.getTitle(), bookDto.getYear(), bookDto.getNovelist().getId());
    }

}
