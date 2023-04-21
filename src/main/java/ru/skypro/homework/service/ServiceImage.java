package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repositories.RepositoryImage;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class ServiceImage {

    private final RepositoryImage repositoryImage;

    public ServiceImage(RepositoryImage repositoryImage) {
        this.repositoryImage = repositoryImage;
    }

    public Image toImage(MultipartFile multipartFile) {
        try {
            Image image = new Image();
            image.setBytes(multipartFile.getBytes());
            repositoryImage.save(image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
