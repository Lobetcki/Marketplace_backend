package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.InvalidParametersExeption;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;

import javax.transaction.Transactional;

@Service
public class ServiceUsers implements UserDetailsManager {
    private final RepositoryUsers repositoryUsers;

    private final RepositoryImage repositoryImage;
    private final PasswordEncoder encoder;

    public ServiceUsers(RepositoryUsers repositoryUsers,
                        RepositoryImage repositoryImage, PasswordEncoder encoder) {
        this.repositoryUsers = repositoryUsers;
        this.repositoryImage = repositoryImage;
        this.encoder = encoder;
    }

    // Обновление пароля
    @Override
    public void changePassword(String oldPassword, String newPassword) {
//        repositoryUsers.existsByPassword(oldPassword, newPassword);
    }
    @Transactional
    public boolean passwordUpdate(String password,
                                  String newPasswordDTO) {
        if (newPasswordDTO == null || newPasswordDTO.isBlank()) {
            throw new InvalidParametersExeption();
        }

        String newPassword = encoder.encode(newPasswordDTO);
//        String authPassword = ((Users) authentication.getPrincipal()).getPassword();
        repositoryUsers.passwordUpdate(password, newPassword);
        return true;
    }
//    @Transactional
//    public boolean passwordUpdate(NewPasswordDTO newPasswordDTO,
//                                  Authentication authentication) {
//        authorizationVerification(authentication);
//        if (newPasswordDTO == null ||
//                newPasswordDTO.getCurrentPassword().isBlank() ||
//                newPasswordDTO.getNewPassword().isBlank()
//        ) {
//            throw new InvalidParametersExeption();
//        }
//
//        String password = encoder.encode(newPasswordDTO.getCurrentPassword());
//        String newPassword = encoder.encode(newPasswordDTO.getNewPassword());
//        String authPassword = ((Users) authentication.getPrincipal()).getPassword();
//        repositoryUsers.passwordUpdate(authPassword, newPassword);
//        if (authPassword.equals(password)) {
//            return true;
//        }
//        return false;

//    }
    // Получить информацию об авторизованном пользователе

    public UserDTO getUser(Authentication authentication) {
//        String login = authorizationVerification(authentication);
//        return UserDTO.fromDTO(repositoryUsers.findByUsername(login));
        return null;
    }

    // Обновить информацию об авторизованном пользователе

    public UserDTO updateUser(UserDTO userDTO, Authentication authentication) {
        Users users = userDTO.toUser();
        users.setUserImage(repositoryImage
                .findById(userDTO.getUserImageUrl()).orElse(null));
        repositoryUsers.save(users);
        return userDTO;
    }
    // Обновить аватар авторизованного пользователя

    public void uploadAvatar(MultipartFile avatarUser) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
