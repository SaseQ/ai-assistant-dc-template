package it.marczuk.aiassistantdctemplate.web.model;

public enum MessageType {

    QUERY("query"),
    SAVE("save"),
    FORGET("forget");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
