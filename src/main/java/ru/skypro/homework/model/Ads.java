package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer price;
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Image adImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL)
    private List<Comments> comments;

}
