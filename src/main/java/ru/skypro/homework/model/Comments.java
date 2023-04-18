package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer pkCommentsId;
    public Instant createdAtDate;
    public String text;


    @ManyToOne
    private Users user;
    @ManyToOne
    private Ads ad;

}
