package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.Users;

public interface RepositoryUsers extends JpaRepository<Users, Long> {

    // Смена пароля
    @Modifying
    @Query(value = "UPDATE users u SET password = ?1 WHERE u.username = ?2", nativeQuery = true)
    void passwordUpdate(String newPassword, String username);

    // Получить информацию об авторизованном пользователе
    Users findByUsernameIgnoreCase(String username);

    // Проверка наличия логина
    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}

