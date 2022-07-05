package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Position {
    private final String position;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public Position(String position, LocalDate startDate, LocalDate endDate, String description) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;

        if (!position.equals(position1.position)) return false;
        if (!startDate.equals(position1.startDate)) return false;
        if (!endDate.equals(position1.endDate)) return false;
        return description != null ? description.equals(position1.description) : position1.description == null;
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "position='" + position + '\'' + "\n" +
                ", startDate=" + DateTimeFormatter.ofPattern("MM/uuuu").format(this.getStartDate()) + "\t" +
                ", endDate=" + DateTimeFormatter.ofPattern("MM/uuuu").format(this.getEndDate()) + "\n" +
                ", description='" + description + '\'' +
                '}' + "\n\n";
    }
}
