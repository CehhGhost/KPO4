package com.example.demo.confid;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Класс, отвечающий за генерацию токенов, валидацию и функций экстракторов
 */
@Service
public class JwtService {

    // Секретный ключ для токенов
    private static final String SECRET_KEY = "5468576D5A7134743777217A25432A46294A404E635266556A586E3272357538782F413F4428472B4B6150645367566B5970337336763979244226452948404D";

    /**
     * Вытаскивает почту пользователя из передаваемого токена
     *
     * @param token JWT токен
     * @return Почта пользователя
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Метод достает требования из токена
     *
     * @param token          JWT токен
     * @param claimsResolver Функция для вытаскивания требований
     * @param <T>            Generic
     * @return Требования из токена
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Метод, генерирующий токен
     *
     * @param userDetails Информация о пользователе
     * @return Сгенерированный токен
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Метод, генерирующий токен
     *
     * @param extraClaims Дополнительные требования
     * @param userDetails Информация о пользователе
     * @return Сгенерированный токен
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Метод для проверки валидности токена
     *
     * @param token       Токен
     * @param userDetails Информация о пользователе
     * @return true - корректен, false - некорректен
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Метод, проверяющий не истек ли еще срок годности токена
     *
     * @param token Токен
     * @return true - не истек срок, false - иначе
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Метод, достающий из токена его срок годности
     *
     * @param token Токен
     * @return Дату из токена
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Метод, достающий все доп требования из токена
     * @param token Токен
     * @return Доп требования из токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Метод, кодирующий по секретному ключу
     * @return Закодированное сообщение
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
