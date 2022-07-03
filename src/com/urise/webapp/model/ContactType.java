package com.urise.webapp.model;

public enum ContactType {
    TEL("Тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN_LINK("Профиль LinkedIn"),
    GIT_LINK("Профиль GitHub"),
    STACK_LINK("Профиль Stackoverflow"),
    HOMEPAGE_LINK("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
