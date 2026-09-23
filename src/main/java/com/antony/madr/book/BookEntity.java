package com.antony.madr.book;

import com.antony.madr.novelist.NovelistEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "novelist_id", nullable = true)
    private NovelistEntity novelist;

    public BookEntity() {
    }

    public BookEntity(String title, Integer year, NovelistEntity novelist) {
        this.title = title;
        this.year = year;
        this.novelist = novelist;
    }


    public Integer getId() {
        return id;
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

    public NovelistEntity getNovelist() {
        return novelist;
    }

    public void setNovelist(NovelistEntity novelist) {
        this.novelist = novelist;
    }
}
