package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ServiceUsers;

@Service
@Qualifier("serviceUsers")
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final ServiceUsers serviceUsers;
    private final RepositoryUsers repositoryUsers;

    public AuthServiceImpl(UserDetailsManager manager,
                           PasswordEncoder passwordEncoder,
                           ServiceUsers serviceUser, RepositoryUsers repositoryUsers) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.serviceUsers = serviceUser;
        this.repositoryUsers = repositoryUsers;
    }

    // Авторизация пользователя
    @Override
    public boolean login(String userName, String password) {
        String encodedPassword = encoder.encode(password);
//    if (manager.userExists(userName)
//            && encoder.matches(password, manager.loadUserByUsername(userName).getPassword())) {
//      return true;
//    }
        RegisterReq users = serviceUsers.loadUserByUsername(userName);
        if (users != null ||
                (users.getPassword()).equals(encodedPassword)) {
            return true;
        }
        return false;
    }

    // Регистрация пользователя
    @Override
    public boolean register(RegisterReq registerReq) {
        if (repositoryUsers.existsByUsername(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(rawPassword -> this.encoder.encode(rawPassword))
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(registerReq.getRole().name())
                        .build());

        registerReq.setPassword(encoder.encode(registerReq.getPassword()));
        serviceUsers.saveUser(registerReq);
        return true;
    }
}