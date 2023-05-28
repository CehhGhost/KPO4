package com.example.demo.user;

import com.example.demo.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.BindException;

/**
 * Класс-сервис, реализующий все запросы контроллера
 */
@Service
@RequiredArgsConstructor
public class UserService {

    // Репозиторий пользователя, работающий с бд
    public final UserRepository userRepository;
    // Репозиторий сессии, работающий с бд
    public final SessionRepository sessionRepository;

    /**
     * Метод, выдающий информацию о пользователе по токену
     *
     * @param request Запрос с токеном
     * @return Возвращает пользователя
     */
    public User getByToken(TokenRequest request) throws BindException {
        var user_id = sessionRepository.findByToken(request.getToken());
        if (user_id.isPresent()) {
            return userRepository.findById(user_id.get().getUser_id()).orElseThrow();
        }
        throw new BindException("Wrong token!");
    }
}
