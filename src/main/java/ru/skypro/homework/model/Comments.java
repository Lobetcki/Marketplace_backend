package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Instant createdAtDate = Instant.now();
    public String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ads ads;

}
