package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.InvalidParametersExeption;
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
    private final MyUserDetailsManager myUserDetailsManager;

    public ServiceUsers(RepositoryUsers repositoryUsers,
                        RepositoryImage repositoryImage,
                        PasswordEncoder encoder,
                        MyUserDetailsManager myUserDetailsManager
    ) {
        this.repositoryUsers = repositoryUsers;
        this.repositoryImage = repositoryImage;
        this.encoder = encoder;
        this.myUserDetailsManager = myUserDetailsManager;
    }

    // Обновление пароля
    @Transactional
    public boolean passwordUpdate(String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new InvalidParametersExeption();
        }
        oldPassword = encoder.encode(oldPassword);
        newPassword = encoder.encode(newPassword);
        if (repositoryUsers.existsByPassword(oldPassword)) {
            throw new InvalidParametersExeption();
//            repositoryUsers.passwordUpdate(oldPassword, newPassword);
//            return true;
        }
        return false;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
        Users user = repositoryUsers.findByUsername("Asd@Asd.com");
//        if (user == null) {
//            throw new UsernameNotFoundException("Not found");
//        }
        return UserDTO.fromDTO(user);
    }

    public Users saveUser(RegisterReq registerReq) {
//        Users users = repositoryUsers.findByUsername(registerReq.getUsername());
        Users users = new Users();
        users.setUsername(registerReq.getUsername());
        users.setPassword(registerReq.getPassword());
        users.setRole(registerReq.getRole());
        users.setFirstName(registerReq.getFirstName());
        users.setLastName(registerReq.getLastName());
        users.setPhone(registerReq.getPhone());
        repositoryUsers.save(users);
        return users;
    }

    // Обновить информацию об авторизованном пользователе
    public UserDTO updateUsersDTO(UserDTO userDTO) {
        Users users = repositoryUsers
                .findByUsername(userDTO.getUsername());
        userDTO.toUser(users);
        repositoryUsers.save(users);
        return userDTO;
    }


    // Обновить аватар авторизованного пользователя
    public String uploadAvatar(MultipartFile avatarUser,
                               Authentication authentication) {
        try {
            Users user = repositoryUsers.findByUsername("Asd@Asd.com");
            repositoryImage.delete(user.getUserImage());
            Image image = new Image();
            image.setBytes(avatarUser.getBytes());
            user.setUserImage(image);
            repositoryImage.save(image);
            Long imageUrl = image.getUrl();
            repositoryUsers.save(user);
            return "https://avatar/" + imageUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
