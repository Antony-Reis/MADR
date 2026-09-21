package com.antony.madr.novelist;

import jakarta.persistence.*;

@Entity
@Table(name = "novelist")
public class NovelistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

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
}
