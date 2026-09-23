package com.antony.madr.novelist;

import com.antony.madr.book.BookEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "novelist")
public class NovelistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "novelist", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private Set<BookEntity> books = new HashSet<>();

    public NovelistEntity() {
    }

    public NovelistEntity(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookEntity> getBooks() {
        return books;
    }
}
