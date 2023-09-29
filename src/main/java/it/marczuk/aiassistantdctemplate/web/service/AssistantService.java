package it.marczuk.aiassistantdctemplate.web.service;

import it.marczuk.aiassistantdctemplate.web.configuration.AssistantConfig;
import it.marczuk.aiassistantdctemplate.web.exception.BadRequestToRestTemplateException;
import it.marczuk.aiassistantdctemplate.web.model.AssistantMessage;
import it.marczuk.aiassistantdctemplate.web.model.AssistantResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Service
public class AssistantService {

    private String WEBHOOK_URL;

    private final AssistantConfig assistantConfig;
    private final RestTemplate restTemplate;

    public AssistantService(AssistantConfig assistantConfig, RestTemplate restTemplate) {
        this.assistantConfig = assistantConfig;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init() {
        WEBHOOK_URL = assistantConfig.getURL();
    }

    public AssistantResponse interactWithAiAssistant(AssistantMessage assistantMessage) {
        return callGetMethod(HttpMethod.POST, getHttpEntity(assistantMessage), AssistantResponse.class);
    }

    private <T> T callGetMethod(HttpMethod method,
                                HttpEntity<?> requestEntity, Class<T> responseType, Object... objects) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(WEBHOOK_URL, method, requestEntity, responseType, objects);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new BadRequestToRestTemplateException(WEBHOOK_URL + " | " + e.getMessage(), e.getStatusCode().toString());
        }
    }

    private HttpEntity<Object> getHttpEntity(AssistantMessage assistantMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(assistantMessage, headers);
    }
}
