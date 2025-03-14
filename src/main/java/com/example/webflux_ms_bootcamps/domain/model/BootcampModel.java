package com.example.webflux_ms_bootcamps.domain.model;

import java.util.List;

public class BootcampModel {
    private Long id;
    private String name;
    private String description;
    private List<CapabilityModel> capabilities;

    public BootcampModel(Long id, String name, String description, List<CapabilityModel> capabilities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capabilities = capabilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CapabilityModel> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityModel> capabilities) {
        this.capabilities = capabilities;
    }
}
