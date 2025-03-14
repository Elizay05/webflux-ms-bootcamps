package com.example.webflux_ms_bootcamps.domain.model;

public class TechnologyModel {
    private Long id;
    private String name;

    public TechnologyModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
