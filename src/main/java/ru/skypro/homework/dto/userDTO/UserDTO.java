package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Image;
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

    public static UserDTO fromDTO(Users users){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(users.getId());
        userDTO.setFirstName(users.getFirstName());
        userDTO.setLastName(users.getLastName());
        userDTO.setPhone(users.getPhone());
        userDTO.setUsername(users.getUsername());
        if (users.getUserImage() == null) {
            Image image = new Image();
//            image.setUrl();
            users.setUserImage(image);
        } else {
//            userDTO.setUserImageUrl(users.getUserImage().getUrl());
        }

        return userDTO;
    }

    public Users toUser(Users users) {
//        Users users = new Users();

//        users.setId(this.getId());
        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());
        users.setUsername(this.getUsername());
        return users;
    }
}
