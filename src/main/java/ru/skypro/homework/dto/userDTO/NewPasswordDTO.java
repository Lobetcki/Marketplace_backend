package ru.skypro.homework.dto.userDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDTO {
    private String currentPassword;
    private String newPassword;
}
