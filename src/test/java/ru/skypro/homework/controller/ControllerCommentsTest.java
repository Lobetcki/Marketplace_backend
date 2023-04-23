package ru.skypro.homework.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skypro.homework.Enams.Role;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comments;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryComments;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.MyUserDetailsManager;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureJsonTesters
public class ControllerCommentsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private RepositoryAds repositoryAds;
    @Autowired
    private RepositoryComments repositoryComments;
    @Autowired
    private MyUserDetailsManager myUserDetailsManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;

    private Authentication authentication;
    private final Users users = new Users();
    private final Ads ads = new Ads();
    private final Comments comment = new Comments();
    private final CommentsDTO commentDTO = new CommentsDTO();

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

        UserDetails userDetails = myUserDetailsManager
                .loadUserByUsername(users.getUsername());

        authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        ads.setTitle("Test ads");
        ads.setDescription("Test test");
        ads.setPrice(150);
        ads.setUsers(users);
        repositoryAds.save(ads);

        comment.setText("Test comment");
        comment.setAds(ads);
        comment.setCreatedAtDate(Instant.now());
        comment.setUsers(users);
        repositoryComments.save(comment);

        commentDTO.setText("Comment test");
    }

    @AfterEach
    void cleatUp() {
        repositoryComments.delete(comment);
        repositoryAds.delete(ads);
        repositoryUsers.delete(users);
    }

    @Test
    public void testGetCommentsByAdId() throws Exception {
        mockMvc.perform(get("/ads/{id}/comments", ads.getId())
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].text").value(comment.getText()));
    }

    @Test
    public void testAddComment() throws Exception {
        MvcResult result = mockMvc.perform(post("/ads/{id}/comments", ads.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO))
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").isNumber())
                .andExpect(jsonPath("$.text")
                        .value(commentDTO.getText()))
                .andExpect(jsonPath("$.authorFirstName")
                        .value(users.getFirstName()))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        AdsDTO createdAd = objectMapper.readValue(responseBody, AdsDTO.class);
        repositoryComments.deleteById(createdAd.getPk());
    }

    @Test
    public void testDeleteComment() throws Exception {
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", ads.getId(), comment.getId())
                        .with(authentication(authentication)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateComment() throws Exception {
        String newText = "New Test comment";
        comment.setText(newText);
        repositoryComments.save(comment);

        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", ads.getId(), comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment))
                        .with((authentication(authentication))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(comment.getText()));
    }
}
