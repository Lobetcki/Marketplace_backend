package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.service.ServiceUsers;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerUsers {

    private final ServiceUsers serviceUsers;

    public ControllerUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

                        // Обновление пароля
    @PostMapping("/set_password")
    public ResponseEntity<?> passwordUpdate(@RequestBody NewPasswordDTO newPasswordDTO
                                            //Authentication authentication
                                            ) {
        if (serviceUsers.passwordUpdate(newPasswordDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

                    // Получить информацию об авторизованном пользователе
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        //Users principal = (Users) authentication.getPrincipal();
        UserDTO userDTO = serviceUsers.getUser(authentication);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

                    // Обновить информацию об авторизованном пользователе
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        UserDTO updatedUser = serviceUsers.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

                    // Обновить аватар авторизованного пользователя
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatarUser(@RequestParam MultipartFile avatarUser) {
        if (avatarUser == null || avatarUser.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        serviceUsers.uploadAvatar(avatarUser);
        return ResponseEntity.ok().build();
    }
}

