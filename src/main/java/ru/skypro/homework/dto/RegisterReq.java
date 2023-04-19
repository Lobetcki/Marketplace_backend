package ru.skypro.homework.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.model.Users;

import java.util.Collection;

@Data
public class RegisterReq implements UserDetails {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public Users toUser() {
        Users users = new Users();
        users.setUsername(this.getUsername());
        users.setPassword(this.getPassword());
        users.setRole(this.);

        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());

        return users;
    }

    public static RegisterReq fromRegisterReq(Users users){
        RegisterReq registerReq = new RegisterReq();

        registerReq.setUsername(users.getUsername());
        registerReq.setPassword(users.getPassword());
        registerReq.setFirstName(users.getFirstName());
        registerReq.setLastName(users.getLastName());
        registerReq.setRole(users.getRole());

        return registerReq;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
