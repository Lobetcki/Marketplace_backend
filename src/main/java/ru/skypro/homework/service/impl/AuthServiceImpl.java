package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.MyUserDetailsManager;
import ru.skypro.homework.service.ServiceUsers;

@Service
@Transactional
@Qualifier("serviceUsers")
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsManager userDetailsManager;
    private final PasswordEncoder encoder;
    private final RepositoryUsers repositoryUsers;

    public AuthServiceImpl(MyUserDetailsManager manager,
                           PasswordEncoder passwordEncoder,
                           ServiceUsers serviceUser, RepositoryUsers repositoryUsers) {
        this.userDetailsManager = manager;
        this.encoder = passwordEncoder;;
        this.repositoryUsers = repositoryUsers;
    }
    // Авторизация пользователя
    @Override
    public boolean login(String userName, String password) {
        if (!userDetailsManager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = userDetailsManager
                .loadUserByUsername(userName);
        return encoder.matches(password,
                userDetails.getPassword());
    }

    // Регистрация пользователя
    @Override
    public boolean register(RegisterReq registerReq) {
        if (userDetailsManager.userExists(registerReq.getUsername())) {
            return false;
        }
        registerReq.setPassword(encoder.encode(registerReq.getPassword()));
        repositoryUsers.save(registerReq.toUser());
        return true;
    }
}