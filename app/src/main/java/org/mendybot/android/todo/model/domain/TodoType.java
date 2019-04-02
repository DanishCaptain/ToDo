package org.mendybot.android.todo.model.domain;

public enum TodoType {
    SHOPPING("S");

    private final String shortName;

    TodoType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
