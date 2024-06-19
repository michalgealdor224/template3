package com.ashcollege.services;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

@Service
public class OpenAIService {

    private final String apiKey = "sk-proj-G8ROJ19zX1ZlbGdJZgxDT3BlbkFJjLBL3eYWknAqu29qRib8";
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", message)));

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        JSONObject responseObject = new JSONObject(response.getBody());
        return responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }
}
