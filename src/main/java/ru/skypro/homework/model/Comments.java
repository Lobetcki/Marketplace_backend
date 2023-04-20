package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;
    public Instant createdAtDate;
    public String text;

    @ManyToOne
    private Users users;
    @ManyToOne
    private Ads ads;

}
