package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;

  private final PasswordEncoder encoder;
  private final RepositoryUsers repositoryUsers;

  public AuthServiceImpl(UserDetailsManager manager,
                         PasswordEncoder passwordEncoder,
                         RepositoryUsers repositoryUsers) {
    this.manager = manager;
    this.encoder = passwordEncoder;
    this.repositoryUsers = repositoryUsers;
  }

  // Авторизация пользователя
  @Override
  public boolean login(String userName, String password) {
    if (manager.userExists(userName) &&
            encoder.matches(password, manager.loadUserByUsername(userName).getPassword())) {
      return true;
    }
    Users users = repositoryUsers.findByLoginEmail(userName);
    if (users != null ||
            repositoryUsers.existsByPassword(this.encoder.encode(password))) {
      return true;
    }
    return false;
  }

  // Регистрация пользователя
  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (manager.userExists(registerReq.getUsername())) {
      return false;
    } else if (repositoryUsers.existsByLoginEmail(registerReq.getUsername())) {
      return false;
    }
    manager.createUser(
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerReq.getPassword())
            .username(registerReq.getUsername())
            .roles(role.name())
            .build());

    Users users = registerReq.toUser();
    users.setRole(role);
    users.setPassword((manager
            .loadUserByUsername(registerReq.getUsername())).getPassword());
    repositoryUsers.save(users);
    return true;
  }
}
