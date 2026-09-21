package com.antony.madr.novelist;


import jakarta.validation.constraints.NotBlank;


public class NovelistDto {
    @NotBlank
    private String name;

    public NovelistDto() {
    }

    public NovelistDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NovelistDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
