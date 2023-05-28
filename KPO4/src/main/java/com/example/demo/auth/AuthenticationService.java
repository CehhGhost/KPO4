package com.example.demo.auth;

import com.example.demo.confid.JwtService;
import com.example.demo.session.Session;
import com.example.demo.session.SessionRepository;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.net.BindException;

/**
 * Класс-сервис, реализующий все запросы контроллера
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    // Репозиторий пользователя, работающий с бд
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    // Репозиторий сессии, работающий с бд
    private final SessionRepository sessionRegistry;

    /**
     * Метод, регистрирующий пользователя в систему
     *
     * @param request Запрос регистрации
     * @return Если такой пользователь еще не зарегистрирован, то регистрирует его и возвращает сообщение об успешной регистрации
     */
    public String register(RegisterRequest request) {
        var probableUser = repository.findByEmail(request.getEmail());
        if (probableUser.isPresent()) {
            throw new RequestRejectedException("User is already registered");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return "You have just correctly registered";
    }

    /**
     * Метод, аутентифицирующий пользователя
     *
     * @param request Запрос аутентификации
     * @return Если данные корректны, то возвращает пользователю токен сессии
     * @throws BindException (error 403), если неправильный пароль
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BindException {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BindException("Wrong password!");
        }
        var jwtToken = jwtService.generateToken(user);
        var session = Session.builder()
                .user_id(user.getId())
                .token(jwtToken)
                .build();
        sessionRegistry.save(session);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
