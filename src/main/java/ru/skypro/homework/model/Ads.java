package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;
    private String description;
    private Integer price;
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Image adImage;

    @ManyToOne
    private Users user;
    @OneToMany(mappedBy = "ad")
    private List<Comments> comments;

}
