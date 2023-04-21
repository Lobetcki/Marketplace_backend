package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Users;

@Data
public class RegisterReq //implements UserDetails
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public static RegisterReq fromRegisterReq(Users users) {
        RegisterReq registerReq = new RegisterReq();

        registerReq.setUsername(users.getUsername());
        registerReq.setPassword(users.getPassword());
        registerReq.setFirstName(users.getFirstName());
        registerReq.setLastName(users.getLastName());
        registerReq.setPhone(users.getPhone());
        registerReq.setRole(users.getRole());

        return registerReq;
    }

    public Users toUser() {
        Users users = new Users();

        users.setUsername(this.getUsername());
        users.setPassword(this.getPassword());
        users.setRole(this.getRole());

        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());

        return users;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
