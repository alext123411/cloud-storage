package com.alext123411.Integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.file.AccessDeniedException;

@Service
public class GitHubService {

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    private static final String gitHubApiBaseUrl = "https://api.github.com/";

    private RestTemplate restTemplate = new RestTemplate();

    public void fetchUser(String token) throws AccessDeniedException {
        String url = gitHubApiBaseUrl + "user";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate
                .exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException ex) {
            System.out.println(ex.getMessage());
            throw new AccessDeniedException(ex.getMessage());
        }

        String responseBody = response.getBody();
        System.out.println(responseBody);
    }
}
