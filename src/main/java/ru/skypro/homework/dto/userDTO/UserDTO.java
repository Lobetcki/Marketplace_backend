package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Users;

@Getter
@Setter
public class UserDTO {
    private Long Id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String userImageUrl;

    public static UserDTO fromDTO(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setFirstName(users.getFirstName());
        userDTO.setLastName(users.getLastName());
        userDTO.setPhone(users.getPhone());
        userDTO.setUsername(users.getUsername());
        if (users.getUserImage() == null) {
            userDTO.setUserImageUrl("No image");
        } else {
            userDTO.setUserImageUrl("https://avatar/"
                    + users.getUserImage().getUrl());
        }
        return userDTO;
    }

    public Users toUser(Users users) {
        users.setId(this.getId());
        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());
        users.setUsername(this.getUsername());
        return users;
    }
}
