package ru.skypro.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private String phone;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Authorities authorities;

    @OneToOne(cascade = CascadeType.ALL)
    private Image userImage;
    @OneToMany(mappedBy = "user")
    private List<Ads> ads;
    @OneToMany(mappedBy = "user")
    private List<Comments> comments;

    private boolean enabled;



}
