package it.marczuk.aiassistantdctemplate.web.model;

public enum MessageGroup {

    MEMORIES("memories"),
    NOTES("notes"),
    LINKS("links"),
    ACTIONS("actions");

    private final String name;

    MessageGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
