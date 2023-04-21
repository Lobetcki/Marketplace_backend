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
        if (encoder.matches(newPasswordDTO.getCurrentPassword(),
                manager.loadUserByUsername(authentication.getName()).getPassword())) {
            throw new InvalidParametersExeption();
        }
        if (repositoryUsers.passwordUpdate(newPasswordDTO.getNewPassword(),
                                            authentication.getName())) {
            return true;
        }
        return false;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
        Users user = repositoryUsers.findByUsernameIgnoreCase(authentication.getName());
        if (user == null) {
            throw new MarketNotFoundException();
        }
        return UserDTO.fromDTO(user);
    }

//    public Users saveUser(RegisterReq registerReq) {
////        Users users = repositoryUsers.findByUsername(registerReq.getUsername());
//        Users users = new Users();
//        users.setUsername(registerReq.getUsername());
//        users.setPassword(registerReq.getPassword());
//        users.setRole(registerReq.getRole());
//        users.setFirstName(registerReq.getFirstName());
//        users.setLastName(registerReq.getLastName());
//        users.setPhone(registerReq.getPhone());
//        repositoryUsers.save(users);
//        return users;
//    }

    // Обновить информацию об авторизованном пользователе
    public UserDTO updateUsersDTO(UserDTO userDTO) {
        Users users = repositoryUsers
                .findByUsernameIgnoreCase(userDTO.getUsername());
        userDTO.toUser(users);
        repositoryUsers.save(users);
        return userDTO;
    }


    // Обновить аватар авторизованного пользователя
    public String uploadAvatar(MultipartFile avatarUser,
                               Authentication authentication) {
        try {
            Users user = repositoryUsers.findByUsernameIgnoreCase("Asd@Asd.com");
            repositoryImage.delete(user.getUserImage());
            Image image = new Image();
            image.setBytes(avatarUser.getBytes());
            repositoryImage.save(image);
//            Long imageUrl = ;
            user.setUserImage(image);
            repositoryUsers.save(user);
            return "/ads/image/" + image.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
