package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.RegisterReq;
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
        repositoryUsers.passwordUpdate(oldPassword, newPassword);
    }

    @Transactional
    public boolean passwordUpdate(String password,
                                  String newPasswordDTO) {
        if (newPasswordDTO == null || newPasswordDTO.isBlank()) {
            throw new InvalidParametersExeption();
        }
        String newPassword = encoder.encode(newPasswordDTO);
        repositoryUsers.existsByPassword(password);
//        String authPassword = ((Users) authentication.getPrincipal()).getPassword();
        changePassword(password, newPassword);
        return true;
    }

    // Получить информацию об авторизованном пользователе
    public UserDTO getUser(Authentication authentication) {
//        loadUserByUsername(authentication.getName());
        return UserDTO.fromDTO(repositoryUsers.findByUsername(authentication.getName()));
    }

    @Override
    public RegisterReq loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users users = repositoryUsers.findByUsername(username);

//        RegisterReq registerReq = new RegisterReq();
//
//        registerReq.setUsername(users.getUsername());
//        registerReq.setPassword(users.getPassword());
//        registerReq.setFirstName(users.getFirstName());
//        registerReq.setLastName(users.getLastName());
//        registerReq.setRole(users.getRole());
        return RegisterReq.fromRegisterReq(repositoryUsers.findByUsername(username));
    }

    @Override
    public void createUser(UserDetails userDetails) {
//        Users users = new Users();
//        users.setUsername(userDetails.getUsername());
//        users.setPassword(userDetails.getPassword());
//        repositoryUsers.save(users);
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

    public UserDTO updateUsersDTO(UserDTO userDTO) {
        Users users = userDTO.toUser();
        users.setUserImage(repositoryImage
                .findById(userDTO.getUserImageUrl()).orElse(null));
        repositoryUsers.save(users);

        return userDTO;
    }


    // Обновить информацию об авторизованном пользователе
    @Override
    public void updateUser(UserDetails userDTO) {
    }

    // Обновить аватар авторизованного пользователя

    public void uploadAvatar(MultipartFile avatarUser) {

    }


    @Override
    public void deleteUser(String username) {
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
