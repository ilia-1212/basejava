package com.urise.webapp.model;

import java.util.ArrayList;

public class Organization {
    private String name;
    private String webSite;
    private ArrayList<Position> positions;

    public String getName() {
        return name;
    }

    public String getWebSite() {
        return webSite;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public Organization(String name, String webSite, ArrayList<Position> positions) {
        this.name = name;
        this.webSite = webSite;
        this.positions = positions;
    }
}
