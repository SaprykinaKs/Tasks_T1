package com.example.creditprocessing.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientInfoApiClient {

    private final RestTemplate restTemplate;
    private final String ms1BaseUrl;

    public ClientInfoApiClient(RestTemplate restTemplate, @Value("${ms1.client-info-url}") String ms1BaseUrl) {
        this.restTemplate = restTemplate;
        this.ms1BaseUrl = ms1BaseUrl;
    }

    public ClientInfo getClientInfo(Long clientId) {
        String url = ms1BaseUrl + "/" + clientId + "/info";
        try {
            return restTemplate.getForObject(url, ClientInfo.class);
        } catch (Exception e) {
            System.err.println("Error calling MS-1: " + e.getMessage());
            return null; 
        }
    }
    
    public record ClientInfo(String fullName, String documentNumber) {}
}