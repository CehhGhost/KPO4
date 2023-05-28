package com.example.demo.session;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Класс репозиторий, осуществляющий связть пользователя с бд
 * Наследуя JpaRepository, получает автореализованный функционал
 */
public interface SessionRepository extends JpaRepository<Session, Integer> {
    // Метод, позволяющий найти сессию в бд по ее токену, автореализован

    /**
     * Метод, позволяющий найти сессию в бд по ее токену, автореализован
     *
     * @return Возможно сессию, если такова найдется
     */
    Optional<Session> findByToken(String session_token);
}
