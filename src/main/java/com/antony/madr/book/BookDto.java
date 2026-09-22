package com.antony.madr.book;

import jakarta.validation.constraints.NotNull;

public class BookDto {

    @NotNull
    private Integer novelistId;

    @NotNull
    private String title;

    @NotNull
    private Integer year;

    public BookDto() {
    }

    public BookDto(Integer novelistId, Integer year, String title) {
        this.novelistId = novelistId;
        this.year = year;
        this.title = title;
    }

    public Integer getNovelistId() {
        return novelistId;
    }

    public void setNovelistId(Integer novelistId) {
        this.novelistId = novelistId;
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

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
