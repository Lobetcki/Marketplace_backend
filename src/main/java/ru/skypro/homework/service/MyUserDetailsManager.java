package ru.skypro.homework.service;

import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repositories.RepositoryUsers;
@Service
public class MyUserDetailsManager implements UserDetailsManager {

    private final RepositoryUsers repositoryUsers;

    public MyUserDetailsManager(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
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
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return repositoryUsers.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
