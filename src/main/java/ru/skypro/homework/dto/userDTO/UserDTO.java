package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;

@Getter
@Setter
public class UserDTO {
    private Long Id;
    private String firstName;
    private String lastName;
    private String phone;
    private String loginEmail;
    private String userImageUrl;

    public static UserDTO fromDTO(Users users){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(users.getId());
        userDTO.setFirstName(users.getFirstName());
        userDTO.setLastName(users.getLastName());
        userDTO.setPhone(users.getPhone());
        userDTO.setLoginEmail(users.getLoginEmail());
        if (users.getUserImage() == null) {
            Image image = new Image();
            image.setUrl("No image");
            users.setUserImage(image);
        }
        userDTO.setUserImageUrl(users.getUserImage().getUrl());

        return userDTO;
    }

    public Users toUser() {
        Users users = new Users();

        users.setId(this.getId());
        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());
        users.setLoginEmail(this.getLoginEmail());
        return users;
    }
}
