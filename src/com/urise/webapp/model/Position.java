package com.urise.webapp.model;

import java.time.LocalDate;

public class Position {
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public String getPosition() {
        return position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public Position(String position, LocalDate startDate, LocalDate endDate, String description) {
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
