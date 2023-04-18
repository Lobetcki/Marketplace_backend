package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.mappers.UsersMapper;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.InvalidParametersExeption;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;

@Service
public class ServiceUsers {

    private final RepositoryUsers repositoryUsers;
    private UsersMapper userMapper;

    public ServiceUsers(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    // Проверка аутентификации
    public boolean authorizationVerification(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return true;
        }
            return false;
    }

    // Обновление пароля
    @Transactional
    public boolean passwordUpdate(NewPasswordDTO newPasswordDTO) {
        if (newPasswordDTO == null ||
                newPasswordDTO.getCurrentPassword().isBlank() ||
                newPasswordDTO.getNewPassword().isBlank()
        ) {
            throw new InvalidParametersExeption("No password");
        }
        repositoryUsers.passwordUpdate(newPasswordDTO.getCurrentPassword(),
                                            newPasswordDTO.getNewPassword());
        return true;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
        Users user = repositoryUsers.findByLoginEmail("wfeew");
        return userMapper.toUserDto(user);
    }
    // Обновить информацию об авторизованном пользователе
    public UserDTO updateUser(UserDTO user) {
        return null;
    }
    // Обновить аватар авторизованного пользователя
    public void uploadAvatar(MultipartFile avatarUser) {

    }
}
