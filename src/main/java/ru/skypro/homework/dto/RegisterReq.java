package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Users;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public Users toUser() {
        Users users = new Users();

        users.setUsername(this.getUsername());
        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());

        return users;
    }
}
