package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.Enams.Role;
import ru.skypro.homework.dto.userDTO.NewPasswordDTO;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.MyUserDetailsManager;
import ru.skypro.homework.service.ServiceUsers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
public class ControllerUsersTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private ServiceUsers serviceUsers;
    @Autowired
    private RepositoryImage repositoryImage;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private MyUserDetailsManager manager;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MyUserDetailsManager myUserDetailsManager;

    private Authentication auth;
    private final Users users = new Users();
    private final MockPart imageFile
            = new MockPart("image", "image", "image".getBytes());
    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        users.setRole(Role.USER);
        users.setFirstName("test FirstName");
        users.setLastName("test LastName");
        users.setPhone("+7123456789");
        users.setUsername("test@test.test");
        users.setPassword("test1234");
        users.setEnabled(true);
        serviceUsers.saveUser(users);

        UserDetails userDetails = myUserDetailsManager.loadUserByUsername(users.getUsername());
        auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @AfterEach
    void deleteObject() {
        serviceUsers.deleteUser(users.getUsername());
        repositoryImage.delete(image);
    }

    @Test
    public void testPasswordUpdated() throws Exception {
        NewPasswordDTO newPassword = new NewPasswordDTO();
        newPassword.setCurrentPassword("test1234");
        newPassword.setNewPassword("1234test");

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPassword))
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/users/me")
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value(users.getUsername()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        String newFirstName = "Test1";
        String newLastName = "Test2";
        users.setFirstName(newFirstName);
        users.setLastName(newLastName);

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users))
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value(newFirstName))
                .andExpect(jsonPath("$.lastName")
                        .value(newLastName));
    }

    @Test
    public void testUpdateUserAvatar() throws Exception {
        mockMvc.perform(patch("/users/me/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(imageFile);
                            return request;
                        })
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }
}

