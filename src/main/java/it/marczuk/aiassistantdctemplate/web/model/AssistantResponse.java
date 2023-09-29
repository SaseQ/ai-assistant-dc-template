package it.marczuk.aiassistantdctemplate.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class AssistantResponse {

    private final String answer;
}
