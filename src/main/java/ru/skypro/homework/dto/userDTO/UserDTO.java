package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;

import java.util.Collection;

@Getter
@Setter
public class UserDTO implements UserDetails {
    private Long Id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
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
            image.setUrl("No image");
            users.setUserImage(image);
        } else {
            userDTO.setUserImageUrl(users.getUserImage().getUrl());
        }

        return userDTO;
    }

    public Users toUser() {
        Users users = new Users();

        users.setId(this.getId());
        users.setFirstName(this.getFirstName());
        users.setLastName(this.getLastName());
        users.setPhone(this.getPhone());
        users.setUsername(this.getUsername());
        return users;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
