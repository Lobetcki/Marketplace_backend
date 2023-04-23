package ru.skypro.homework.controller;

import org.springframework.stereotype.Service;
import ru.skypro.homework.Enams.Role;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;

@Service
public class TestUsers {

    private static RepositoryUsers repositoryUsers;

//    public TestUsers(RepositoryUsers repositoryUsers) {
//        this.repositoryUsers = repositoryUsers;
//    }

    public static void toUser() {
    Users users = new Users();
        users.setRole(Role.USER);
        users.setFirstName("FirstName");
        users.setLastName("LastName");
        users.setPhone("+71234561111");
        users.setUsername("test1@test.com");
        users.setPassword("test1234");
        users.setEnabled(true);
        repositoryUsers.save(users);


    }




}
