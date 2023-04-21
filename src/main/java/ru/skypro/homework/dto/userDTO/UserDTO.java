package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Users;

@Getter
@Setter
public class UserDTO {
    private Long Id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;

    public static UserDTO fromDTO(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setFirstName(users.getFirstName());
        userDTO.setLastName(users.getLastName());
        userDTO.setPhone(users.getPhone());
        userDTO.setEmail(users.getUsername());
        if (users.getUserImage() == null) {
            userDTO.setImage("No image");
        } else {
            userDTO.setImage("/ads/image/"
                    + users.getUserImage().getId());
        }
        return userDTO;
    }
}
