package com.antony.madr.book;

public class BookResponseDto {
    private String title;
    private Integer year;

    public BookResponseDto() {
    }

    public BookResponseDto(String title, Integer year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
