package ru.skypro.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.Enams.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    private Image userImage;
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Ads> ads;
    @OneToMany(mappedBy = "users")
    private List<Comments> comments;
}
