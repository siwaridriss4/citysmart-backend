package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.smartcity.model.Badge;
import tn.smartcity.model.User;
import tn.smartcity.repository.BadgeRepository;
import tn.smartcity.repository.ClaimRepository;
import tn.smartcity.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;
    private final BadgeRepository badgeRepository;

    @Transactional
    public void addPointsForClaim(User user, int points) {
        user.addPoints(points);
        userRepository.save(user);
        
        checkAndAwardBadges(user);
    }

    @Transactional
    public void checkAndAwardBadges(User user) {
        Long claimCount = claimRepository.countByUser(user);
        
        // Badge "Premier Signalement"
        if (claimCount == 1) {
            awardBadge(user, "first_claim");
        }
        
        // Badge "Citoyen Engagé"
        if (claimCount == 5) {
            awardBadge(user, "citizen_engaged");
        }
        
        // Badge "Gardien du Quartier"
        if (claimCount == 10) {
            awardBadge(user, "neighborhood_guardian");
        }
        
        // Badge "Super Citoyen"
        if (claimCount == 50) {
            awardBadge(user, "super_citizen");
        }
        
        // Badge "Héros de la Ville"
        if (claimCount == 100) {
            awardBadge(user, "city_hero");
        }
    }

    @Transactional
    public void awardBadge(User user, String badgeCode) {
        badgeRepository.findByCode(badgeCode).ifPresent(badge -> {
            if (!user.getBadges().contains(badge)) {
                user.addBadge(badge);
                user.addPoints(badge.getPointsReward());
                userRepository.save(user);
            }
        });
    }
}
