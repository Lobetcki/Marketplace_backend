package ru.skypro.homework.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long url;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;
}
