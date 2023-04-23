package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.Enams.Role;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.ServiceUsers;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private RepositoryUsers repositoryUsers;
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private ServiceUsers serviceUsers;
    @Autowired
    private ObjectMapper objectMapper;

    private final Users users = new Users();
    private final RegisterReq registerReq = new RegisterReq();

    @Test
    public void testRegister() throws Exception {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername("test@test.test");
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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "test@test.test");
        jsonObject.put("password", "password2");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk());

        serviceUsers.deleteUser(registerReq.getUsername());
    }

}