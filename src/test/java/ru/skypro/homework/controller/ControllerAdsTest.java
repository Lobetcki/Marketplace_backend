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
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;
import ru.skypro.homework.service.MyUserDetailsManager;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ControllerAdsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RepositoryAds repositoryAds;
    @Autowired
    private RepositoryUsers repositoryUsers;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private MyUserDetailsManager myUserDetailsManager;
    @Autowired
    private RepositoryImage repositoryImage;

    private Authentication auth;
    private final MockPart imageFile
            = new MockPart("image", "image", "image".getBytes());
    private final Users users = new Users();
    private final CreateAdsDTO createAds = new CreateAdsDTO();
    private final Ads ads = new Ads();
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
        repositoryUsers.save(users);

        UserDetails userDetails = myUserDetailsManager
                .loadUserByUsername(users.getUsername());
        auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        createAds.setTitle("Test ads");
        createAds.setDescription("Test test");
        createAds.setPrice(150);

        ads.setTitle("Test ads");
        ads.setDescription("Test test");
        ads.setPrice(150);
        ads.setUsers(users);
        repositoryAds.save(ads);
    }

    @AfterEach
    void cleanUp() {
        repositoryUsers.delete(users);
    }

    @Test
    public void testGetAllAds() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber());
//                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    public void testCreateAd() throws Exception {
        MockPart created = new MockPart("properties", objectMapper.writeValueAsBytes(createAds));

        mockMvc.perform(multipart("/ads")
                        .part(imageFile)
                        .part(created)
                        .with(authentication(auth)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pk").isNotEmpty())
                .andExpect(jsonPath("$.pk").isNumber())
                .andExpect(jsonPath("$.title").value(createAds.getTitle()));
//                .andExpect(jsonPath("$.description").value(createAds.getDescription()))
//                .andExpect(jsonPath("$.price").value(createAds.getPrice()));
    }

    @Test
    public void testGetAdById() throws Exception {
        mockMvc.perform(get("/ads/{id}", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ads.getId()))
                .andExpect(jsonPath("$.title").value(ads.getTitle()))
                .andExpect(jsonPath("$.description").value(ads.getDescription()))
                .andExpect(jsonPath("$.price").value(ads.getPrice()))
                .andExpect(jsonPath("$.email").value(users.getUsername()))
                .andExpect(jsonPath("$.authorFirstName").value(users.getFirstName()))
                .andExpect(jsonPath("$.authorLastName").value(users.getLastName()))
                .andExpect(jsonPath("$.phone").value(users.getPhone()));
    }

    @Test
    public void testDeleteAd() throws Exception {
        mockMvc.perform(delete("/ads/{id}", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAd() throws Exception {
        String newTitle = "Ads test";
        String newDesc = "Test test2";
        Integer newPrice = 1555;
        ads.setTitle(newTitle);
        ads.setDescription(newDesc);
        ads.setPrice(newPrice);
        repositoryAds.save(ads);

        mockMvc.perform(patch("/ads/{id}", ads.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ads))
                        .with((authentication(auth))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newTitle))
                .andExpect(jsonPath("$.description").value(newDesc))
                .andExpect(jsonPath("$.price").value(newPrice));
    }

    @Test
    public void tesGetAdsByCurrentUser() throws Exception {
        mockMvc.perform(get("/ads/me")
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    public void testUpdateAdImage() throws Exception {
        mockMvc.perform(patch("/ads/{id}/image", ads.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(imageFile);
                            return request;
                        })
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetImage() throws Exception {
        image.setBytes("image".getBytes());
        repositoryImage.save(image);
        ads.setAdImage(image);
        repositoryAds.save(ads);

        mockMvc.perform(get("/ads/image/{id}", image.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(content().bytes(image.getBytes()));
    }



}
