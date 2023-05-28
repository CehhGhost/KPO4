package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Класс репозиторий, осуществляющий связть пользователя с бд
 * Наследуя JpaRepository, получает автореализованный функционал
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Метод, позволяющий найти пользователя в бд по его email, автореализован
     *
     * @return Возможно пользователя, если такой найдется
     */
    Optional<User> findByEmail(String email);
}
