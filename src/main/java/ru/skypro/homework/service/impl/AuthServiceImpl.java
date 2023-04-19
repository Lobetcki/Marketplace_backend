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
    String encodedPassword = encoder.encode(password);
//    if (manager.userExists(userName)
//            && encoder.matches(password, manager.loadUserByUsername(userName).getPassword())) {
//      return true;
//    }
    Users users = repositoryUsers.findByUsername(userName);
    if (users != null ||
            repositoryUsers.existsByPassword(encodedPassword)) {
      return true;
    }
    return false;
  }

  // Регистрация пользователя
  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (manager.userExists(registerReq.getUsername())
            || repositoryUsers.existsByUsername(registerReq.getUsername())) {
      return false;
    }
//    String encodedPassword = encoder.encode(registerReq.getPassword());
    manager.createUser(
            User.builder()
                    .passwordEncoder(this.encoder::encode)
                    .password(registerReq.getPassword())
                    .username(registerReq.getUsername())
                    .roles(role.name())
                    .build());
    Users users = repositoryUsers.findByUsername(registerReq.getUsername());
    users.setRole(role);
    users.setFirstName(registerReq.getFirstName());
    users.setLastName(registerReq.getLastName());
    users.setPhone(registerReq.getPhone());
    repositoryUsers.save(users);
    return true;
  }
}