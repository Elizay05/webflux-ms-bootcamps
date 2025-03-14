package com.example.webflux_ms_bootcamps.domain.model;

import java.util.List;

public class BootcampPageModel {
    private List<BootcampModel> bootcamps;
    private int totalPages;
    private long totalElements;

    public BootcampPageModel(List<BootcampModel> bootcamps, int totalPages, long totalElements) {
        this.bootcamps = bootcamps;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<BootcampModel> getBootcamps() {
        return bootcamps;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setBootcamps(List<BootcampModel> bootcamps) {
        this.bootcamps = bootcamps;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
