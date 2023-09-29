package it.marczuk.aiassistantdctemplate.web.model;

import lombok.Data;

@Data
public class AssistantMessage {

    private final String query;
    private final MessageType type;
    private final MessageGroup group;
}
