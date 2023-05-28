package com.example.demo.session;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс сессии
 * Аннотация Entity сохраняет класс, как часть датабазы в таблице "_session"
 * Отсутствуют поля:
 * expires_at (поскольку в токен встроенна данная информация, а он уже здесь храниться)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_session")
public class Session {
    // Индефикационный номер сессии в базе данных
    @Id
    @GeneratedValue
    private Integer id;
    // id пользователя сессии
    private Integer user_id;
    // Токен
    private String token;

    /**
     * Геттер для user_id
     *
     * @return user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * Геттер для token
     *
     * @return token
     */
    public String getToken() {
        return token;
    }
}
