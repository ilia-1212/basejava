package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrganizationSection extends Section {
    private ArrayList<Organization> content;

    public OrganizationSection(ArrayList<Organization> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String text = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/uuuu");
        for (Organization organization : content) {
            text += organization.getName() + " url = " + organization.getWebSite() + "\n";

            for (Position position : organization.getPositions()) {
                String dateEndText = "";
                if (position.getEndDate() == LocalDate.now()) {
                    dateEndText = "Сейчас";
                 } else {
                     dateEndText = dtf.format(position.getEndDate());
                 }
                text += dtf.format(position.getStartDate()) + " - " + dateEndText  + "\t" +
                position.getPosition() + "\n" + position.getDescription() + "\n";
            }
        }

        return text;
    }
}
