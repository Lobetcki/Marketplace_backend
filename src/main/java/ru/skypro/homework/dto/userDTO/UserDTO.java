package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
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
        userDTO.setUserImageUrl(users.getUserImage().getUrl());

        return userDTO;
    }
}
