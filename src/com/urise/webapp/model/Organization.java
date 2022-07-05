package com.urise.webapp.model;

import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<Position> positions;

    public Organization(String name, String url, List<Position> positions) {
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions != null ? positions.equals(that.positions) : that.positions == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage + "\n" +
                ", positions=" + positions +
                '}' + "\n";
    }
}
