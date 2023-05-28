package com.example.demo.confid;

import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс-Конфигуратор для Spring Security
 * Содержит настройки Authentication Manager и Authentication Provider, используемые для аутентификации и авторизации пользователей в приложении
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    // Репозиторий пользователя, работающий с бд
    private final UserRepository repository;

    /**
     * Метод, который ищет пользователя в базе данных по его электронной почте
     *
     * @return Возвращает его объект
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Создает экземпляр `DaoAuthenticationProvider`, который используется для аутентификации пользователей
     *
     * @return экземпляр `DaoAuthenticationProvider`
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Создает экземпляр AuthenticationManager, который является ядром безопасности Spring Security
     * @throws Exception
     * @return экземпляр AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Создает объект, который используется для хэширования паролей пользователей
     *
     * @return экземпляр BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
