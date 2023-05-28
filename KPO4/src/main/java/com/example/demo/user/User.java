package com.example.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Класс пользователя, который имплементирует UserDetails, чтобы быть использованным в Spring Security
 * Аннотация Entity сохраняет класс, как часть датабазы в таблице "_user"
 * Отсутствуют поля:
 * username (поскольку не имеет смысла ввиду того, что пользователей отличают по почте)
 * created_at и updated_at (поскольку в работе нигде нет им приминения, так что и смысла в них нет)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    // Индефикационный номер пользователя в базе данных
    @Id
    @GeneratedValue
    private Integer id;
    // Имя
    private String firstname;
    // Фамилия
    private String lastname;
    // Почта
    private String email;
    // Пароль (в базе данных храниться в хэшированном виде)
    private String password;
    // Поль пользователя в системе (админ или обычный пользователь)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Метод, перегружающий метод из UserDetails
     *
     * @return Список SimpleGrantedAuthority, содержащий роль пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Геттер для пароля
     *
     * @return Пароль
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Геттер для почты
     *
     * @return Почта
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Перегруженный метод из UserDetails
     * @return Говорит, что аккаунт пользователя без срока годности
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Перегруженный метод из UserDetails
     * @return Говрит, что аккаунт не блокируем
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Перегруженный метод из UserDetails
     * @return Говрит, что пароль аккаунта без срока годности
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Перегруженный метод из UserDetails
     * @return Говрит, что аккаунт всегда доступен
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
