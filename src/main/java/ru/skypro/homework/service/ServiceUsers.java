package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.InvalidParametersExeption;
import ru.skypro.homework.exception.MarketNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class ServiceUsers {
    private final RepositoryUsers repositoryUsers;
    private final RepositoryImage repositoryImage;
    private final PasswordEncoder encoder;
    private final MyUserDetailsManager manager;

    public ServiceUsers(RepositoryUsers repositoryUsers,
                        RepositoryImage repositoryImage,
                        PasswordEncoder encoder,
                        MyUserDetailsManager manager) {
        this.repositoryUsers = repositoryUsers;
        this.repositoryImage = repositoryImage;
        this.encoder = encoder;
        this.manager = manager;
    }

    // Обновление пароля
    @Transactional
    public boolean passwordUpdate(NewPasswordDTO newPasswordDTO,
                                  Authentication authentication) {
        if (newPasswordDTO.getNewPassword() == null
                || newPasswordDTO.getNewPassword().isBlank()) {
            throw new InvalidParametersExeption();
        }
        if (!(encoder.matches(newPasswordDTO.getCurrentPassword(),
                (manager.loadUserByUsername(authentication.getName()).getPassword())))) {
            throw new InvalidParametersExeption();
        }
        repositoryUsers.passwordUpdate(encoder.encode(newPasswordDTO.getNewPassword()),
                                        authentication.getName());
        return true;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
        Users user = repositoryUsers.findByUsernameIgnoreCase(authentication.getName());
        if (user == null) {
            throw new MarketNotFoundException();
        }
        return UserDTO.fromDTO(user);
    }

    // Обновить информацию об авторизованном пользователе
    public UserDTO updateUsersDTO(UserDTO userDTO, Authentication authentication) {
        Users users = repositoryUsers
                .findByUsernameIgnoreCase(authentication.getName());
        users.setFirstName(userDTO.getFirstName());
        users.setLastName(userDTO.getLastName());
        users.setPhone(userDTO.getPhone());
        repositoryUsers.save(users);
        return UserDTO.fromDTO(users);
    }

    // Обновить аватар авторизованного пользователя
    public String uploadAvatar(MultipartFile avatarUser,
                               Authentication authentication) {
        try {
            Users users = repositoryUsers
                    .findByUsernameIgnoreCase(authentication.getName());
            if (users.getUserImage() != null) {
                repositoryImage.delete(users.getUserImage());
            }
            Image image = new Image();
            image.setBytes(avatarUser.getBytes());
            repositoryImage.save(image);
            users.setUserImage(image);
            repositoryUsers.save(users);
            return "/ads/me/image/" + image.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteUser(String username){
        repositoryUsers.deleteByUsername(username);
    }
}
