package ru.skypro.homework.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class Image {

    @Id
    private String url;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;
    private String filePath;
    private long fileSize;
    private String mediaType;
}
