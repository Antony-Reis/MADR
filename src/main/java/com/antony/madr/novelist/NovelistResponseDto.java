package com.antony.madr.novelist;

import com.antony.madr.book.BookResponseDto;

import java.util.Set;

public class NovelistResponseDto {
    private String name;
    private Set<BookResponseDto> books;

    public NovelistResponseDto() {
    }

    public NovelistResponseDto(String name, Set<BookResponseDto> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookResponseDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookResponseDto> books) {
        this.books = books;
    }
}
