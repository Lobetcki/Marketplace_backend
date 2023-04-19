package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ServiceUsers;

import java.security.Security;

@Service
@Qualifier("serviceUsers")
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;
  private final PasswordEncoder encoder;
  private final ServiceUsers serviceUsers;

  public AuthServiceImpl(UserDetailsManager manager,
                         PasswordEncoder passwordEncoder,
                         ServiceUsers serviceUser) {
    this.manager = manager;
    this.encoder = passwordEncoder;
    this.serviceUsers = serviceUser;
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
            users.equals(encodedPassword)) {
      return true;
    }
    return false;
  }

  // Регистрация пользователя
  @Override
  public boolean register(RegisterReq registerReq) {
    if (serviceUsers.loadUserByUsername(
            registerReq.getUsername()) != null) {
      return false;
    }
    manager.createUser(
            User.builder()
                    .passwordEncoder(this.encoder::encode)
                    .password(registerReq.getPassword())
                    .username(registerReq.getUsername())
//                    .authorities(role.name())
                    .roles(registerReq.getRole().name())
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