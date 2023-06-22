package com.example.orders.services;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/***
 * Класс для установления соединения с API микросервиса авторизации.
 ***/
@Service
@AllArgsConstructor
public class ConnectionService {
    /***
     * Метод для получения информации о пользователе по его токену.
     * @param token - токен пользователя.
     * @return - информацию о пользователе.
     * @throws URISyntaxException - исключение, возникающее при неправильном формате URI.
     ***/
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
    /***
     * Метод для получения идентификатора пользователя по его токену.
     * @param token - токен пользователя.
     * @return - идентификатор пользователя.
     * @throws URISyntaxException - исключение, возникающее при неправильном формате URI.
     ***/
    public Integer getUserIdByToken(String token) throws URISyntaxException {
        String result = getUserByToken(token);
        assert result != null;
        var string = result.substring(result.indexOf("\"id\":") + 5, result.indexOf(",\"firstname\""));
        return Integer.parseInt(string);
    }
    /***
     * Метод для получения электронной почты пользователя по его токену.
     * @param token - токен пользователя.
     * @return - электронную почту пользователя.
     * @throws URISyntaxException - исключение, возникающее при неправильном формате URI.
     ***/
    public String getUserEmailByToken(String token) throws URISyntaxException {
        String result = getUserByToken(token);
        assert result != null;
        return result.substring(result.indexOf("\"email\":") + 9, result.indexOf(",\"password\"") - 1);
    }
}
