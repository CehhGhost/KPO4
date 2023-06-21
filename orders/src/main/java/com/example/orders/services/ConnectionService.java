package com.example.orders.services;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@AllArgsConstructor
public class ConnectionService {
    public String getUserByToken(String token) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        URI userInfoUri = new URI("http://localhost:8080/api/v1/user/get");
        ResponseEntity<String> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
    public Integer getUserIdByToken(String token) throws URISyntaxException {
        String result = getUserByToken(token);
        assert result != null;
        var string = result.substring(result.indexOf("\"id\":") + 5, result.indexOf(",\"firstname\""));
        return Integer.parseInt(string);
    }
    public String getUserEmailByToken(String token) throws URISyntaxException {
        String result = getUserByToken(token);
        assert result != null;
        return result.substring(result.indexOf("\"email\":") + 9, result.indexOf(",\"password\"") - 1);
    }
}
