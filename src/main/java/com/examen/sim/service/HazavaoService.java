package com.examen.sim.service;

import com.examen.sim.model.DefinitionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class HazavaoService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HazavaoService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public DefinitionResponse getDefinition(String teny) {
        try {
            String prompt = String.format(
                    "Donnez la définition du mot '%s' en malgache. " +
                            "Répondez uniquement avec la définition en malgache, sans explication supplémentaire.",
                    teny
            );

            String definition = callChatGPT(prompt);
            return new DefinitionResponse(teny, definition);

        } catch (Exception e) {
            return new DefinitionResponse(teny, "Tsy hita ny famaritana an'ity teny ity.");
        }
    }

    private String callChatGPT(String prompt) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format("""
            {
                "model": "gpt-3.5-turbo",
                "messages": [
                    {
                        "role": "user",
                        "content": "%s"
                    }
                ],
                "max_tokens": 150,
                "temperature": 0.7
            }
            """, prompt.replace("\"", "\\\""));

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.path("choices").get(0).path("message").path("content").asText().trim();
    }
}
