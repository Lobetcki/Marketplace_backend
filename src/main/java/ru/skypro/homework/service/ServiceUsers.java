package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.InvalidParametersExeption;
import ru.skypro.homework.exception.UnauthorizedExeption;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;

import javax.transaction.Transactional;

@Service
public class ServiceUsers {

    private final RepositoryUsers repositoryUsers;
    private final RepositoryImage repositoryImage;
    private final PasswordEncoder encoder;

    public ServiceUsers(RepositoryUsers repositoryUsers,
                        RepositoryImage repositoryImage, PasswordEncoder encoder) {
        this.repositoryUsers = repositoryUsers;
        this.repositoryImage = repositoryImage;
        this.encoder = encoder;
    }

    // Проверка аутентификации
    public String authorizationVerification(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedExeption();
        }
        return authentication.getName();
    }

    // Обновление пароля
    @Transactional
    public boolean passwordUpdate(NewPasswordDTO newPasswordDTO,
                                  Authentication authentication) {
        authorizationVerification(authentication);
        if (newPasswordDTO == null ||
                newPasswordDTO.getCurrentPassword().isBlank() ||
                newPasswordDTO.getNewPassword().isBlank()
        ) {
            throw new InvalidParametersExeption();
        }

        String password = encoder.encode(newPasswordDTO.getCurrentPassword());
        String newPassword = encoder.encode(newPasswordDTO.getNewPassword());

        if (repositoryUsers.existsByPassword(password)) {
            repositoryUsers.passwordUpdate(password, newPassword);
            return true;
        }
        return false;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
        String login = authorizationVerification(authentication);
        return UserDTO.fromDTO(repositoryUsers.findByLoginEmail(login));
    }

    // Обновить информацию об авторизованном пользователе
    public UserDTO updateUser(UserDTO userDTO, Authentication authentication) {
        Users users = userDTO.toUser();
        users.setUserImage(repositoryImage
                .findById(authorizationVerification(authentication)).orElse(null));
        repositoryUsers.save(users);
        return userDTO;
    }

    // Обновить аватар авторизованного пользователя
    public void uploadAvatar(MultipartFile avatarUser) {

    }
}
