package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageService {

    public String uploadImage(MultipartFile file) {
        // TODO: Implémenter MinIO ou S3
        // Pour l'instant, retourne une URL fictive
        return "https://storage.smartcity.tn/images/" + file.getOriginalFilename();
    }

    public void deleteImage(String imageUrl) {
        // TODO: Implémenter la suppression d'image
    }
}
