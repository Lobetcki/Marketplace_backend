package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.Users;

public interface RepositoryUsers extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    // Обновление пароля
    boolean existsByPassword(String password);
    @Modifying
    @Query(value = "UPDATE users u SET password = ?2 WHERE u.password = ?1", nativeQuery = true)
    void passwordUpdate(String password, String newPassword);

    // Получить информацию об авторизованном пользователе
    Users findByUsername(String loginEmail);

    // Проверка наличия логина
    boolean existsByUsername(String loginEmail);
}

