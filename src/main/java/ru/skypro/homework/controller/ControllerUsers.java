package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.service.ServiceUsers;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerUsers {

    private final ServiceUsers serviceUsers;

    public ControllerUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }


    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", tags = "Пользователи",
            requestBody = @RequestBody(description = "DTO для обновления пароля пользователя",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewPasswordDTO.class))}),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<?> passwordUpdate(@RequestBody NewPasswordDTO newPasswordDTO,
                                            Authentication authentication) {
        if (serviceUsers.passwordUpdate(newPasswordDTO, authentication)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", tags = "Пользователи",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content), //где получить?
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        UserDTO userDTO = serviceUsers.getUser(authentication);
        return ResponseEntity.ok(userDTO);
    }


    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", tags = "Пользователи",
            requestBody = @RequestBody(description = "DTO с обновленными данными пользователя",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(responseCode = "204", description = "No Content")
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content), //где получить?
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user,
                                              Authentication authentication) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        UserDTO userDTO = serviceUsers.updateUsersDTO(user, authentication);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", tags = "Пользователи",
            requestBody = @RequestBody(description = "Файл изображения для обновления аватара пользователя",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary"))}),
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<String> uploadAvatarUser(
            @RequestParam MultipartFile avatarUser,
            Authentication authentication) {
        return ResponseEntity.ok(serviceUsers.uploadAvatar(avatarUser, authentication));
    }
}

