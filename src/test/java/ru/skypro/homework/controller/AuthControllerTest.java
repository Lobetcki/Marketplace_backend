package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.MarketNotFoundException;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ServiceUsers;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.checkerframework.checker.nullness.Opt.orElseThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;

    private final LoginReq req = new LoginReq();
    private final Users users = new Users();
    private final RegisterReq registerReq = new RegisterReq();

    @BeforeEach
    void setUp() {
        users.setRole(Role.USER);
        users.setFirstName("test FirstName");
        users.setLastName("test LastName");
        users.setPhone("+7123456789");
        users.setUsername("test@test.test");
        users.setPassword("test1234");
        users.setEnabled(true);
        repositoryUsers.save(users);

        req.setUsername("test@test.test");
        req.setPassword("+7123456789");

        registerReq.setUsername("testUser");
        registerReq.setPassword("testPassword");
        registerReq.setFirstName("User2");
        registerReq.setLastName("Test2");
        registerReq.setPhone("+79609279285");
        registerReq.setPassword("password2");
        registerReq.setRole(Role.ADMIN);


    }

    @AfterEach
    public void deleteUsers(){
        repositoryUsers.delete(users);
    }

    @Test
    public void testLoginReturnsValidCredentialsWhenLoginSuccess() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterReturnsValidCredentialsWhenRegisterSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andExpect(status().isOk());

        Users user = repositoryUsers
                .findByUsernameIgnoreCase(registerReq.getUsername());
//                .orElseThrow(MarketNotFoundException::new);
        repositoryUsers.delete(user);
    }

}