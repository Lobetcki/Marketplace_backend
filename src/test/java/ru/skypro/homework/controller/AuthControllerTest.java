package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.Enams.Role;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;

//    private final LoginReq req = new LoginReq();
//    private final Users users = new Users();
//    private final RegisterReq registerReq = new RegisterReq();

//    @BeforeEach
//    void setUp() {
//        users.setRole(Role.USER);
//        users.setFirstName("test FirstName");
//        users.setLastName("test LastName");
//        users.setPhone("+7123456789");
//        users.setUsername("test@test.test");
//        users.setPassword("test1234");
//        users.setEnabled(true);
//        repositoryUsers.save(users);
//
//        req.setUsername("test@test.test");
//        req.setPassword("+7123456789");
//
//        registerReq.setUsername("testUser");
//        registerReq.setPassword("testPassword");
//        registerReq.setFirstName("User2");
//        registerReq.setLastName("Test2");
//        registerReq.setPhone("+79609279285");
//        registerReq.setPassword("password2");
//        registerReq.setRole(Role.ADMIN);
//    }

//    @AfterEach
//    public void deleteUsers(){
//        repositoryUsers.delete(users);
//    }


    @Test
    public void testLogin() throws Exception {
        Users users = new Users();
        users.setRole(Role.USER);
        users.setFirstName("FirstName");
        users.setLastName("LastName");
        users.setPhone("+71234561111");
        users.setUsername("test1@test.com");
        users.setPassword("test1234");
        users.setEnabled(true);
        System.out.println(users.toString());
        repositoryUsers.save(users);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "test1@test.com");
        jsonObject.put("password", "test1234");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister() throws Exception {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername("testUser");
        registerReq.setPassword("testPassword");
        registerReq.setFirstName("User2");
        registerReq.setLastName("Test2");
        registerReq.setPhone("+79609279285");
        registerReq.setPassword("password2");
        registerReq.setRole(Role.ADMIN);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andExpect(status().isOk());

//        Users user = repositoryUsers
//                .findByUsernameIgnoreCase(registerReq.getUsername());
////                .orElseThrow(MarketNotFoundException::new);
//        repositoryUsers.delete(user);
    }

}