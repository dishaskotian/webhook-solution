package com.example.webhook;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class WebhookService {

    @Value("${app.name}")
    private String name;

    @Value("${app.regNo}")
    private String regNo;

    @Value("${app.email}")
    private String email;

    private final String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    public void execute() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        
        ObjectNode reqJson = mapper.createObjectNode();
        reqJson.put("name", name);
        reqJson.put("regNo", regNo);
        reqJson.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(reqJson.toString(), headers);

        
        ResponseEntity<String> response = restTemplate.postForEntity(generateUrl, request, String.class);

        System.out.println("Response from generateWebhook:");
        System.out.println(response.getBody());

       
        JsonNode json = mapper.readTree(response.getBody());
        String webhook = json.get("webhook").asText();
        String token = json.get("accessToken").asText();

        
        String finalQuery = Files.readString(Path.of("src/main/resources/final_query.sql")).trim();

       
        ObjectNode submitJson = mapper.createObjectNode();
        submitJson.put("finalQuery", finalQuery);

        HttpHeaders submitHeaders = new HttpHeaders();
        submitHeaders.setContentType(MediaType.APPLICATION_JSON);
        submitHeaders.set("Authorization", token);

        HttpEntity<String> submitReq = new HttpEntity<>(submitJson.toString(), submitHeaders);

        
        ResponseEntity<String> submitResponse = restTemplate.postForEntity(webhook, submitReq, String.class);

        System.out.println("Submission Response:");
        System.out.println(submitResponse.getBody());
    }
}
