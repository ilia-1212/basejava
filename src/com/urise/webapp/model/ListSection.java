package com.urise.webapp.model;

import java.util.ArrayList;

public class ListSection extends Section {
    private ArrayList<String> content;

    public ListSection(ArrayList<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String text = "";
        for (String line : content) {
            text += "*  " + line + "\n";
        }
        return text;
    }
}
