package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.homework.model.Users;

public interface RepositoryUsers extends JpaRepository<Users, Long> {

    @Modifying
    @Query("UPDATE Users u SET u.password = :newPassword WHERE u.username = :username")
    boolean passwordUpdate(@Param("newPassword") String newPassword, @Param("username") String username);

    // Получить информацию об авторизованном пользователе
    Users findByUsernameIgnoreCase(String loginEmail);

    // Проверка наличия логина
    boolean existsByUsername(String loginEmail);
}

