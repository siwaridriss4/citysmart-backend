package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    public String detectCategory(String imageUrl) {
        // TODO: Implémenter Google Cloud Vision API
        // Pour l'instant, retourne null
        return null;
    }

    public Double calculateConfidence(String imageUrl) {
        // TODO: Implémenter le calcul de confiance IA
        return null;
    }

    public boolean moderateContent(String text, String imageUrl) {
        // TODO: Implémenter la modération de contenu
        return false;
    }
}
