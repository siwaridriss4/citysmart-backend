package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.smartcity.model.Notification;
import tn.smartcity.model.User;
import tn.smartcity.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendClaimCreatedNotification(User user, Long claimId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Réclamation créée");
        notification.setBody("Votre réclamation a été créée avec succès");
        notification.setType("CLAIM_CREATED");
        notification.setReferenceId(claimId);
        notification.setIsRead(false);
        notification.setIsSent(false);
        
        notificationRepository.save(notification);
        
        // TODO: Envoyer via Firebase Cloud Messaging
    }

    @Transactional
    public void sendClaimAssignedNotification(User user, Long claimId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Réclamation assignée");
        notification.setBody("Un agent a été assigné à votre réclamation");
        notification.setType("CLAIM_ASSIGNED");
        notification.setReferenceId(claimId);
        
        notificationRepository.save(notification);
    }

    @Transactional
    public void sendClaimResolvedNotification(User user, Long claimId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Réclamation résolue");
        notification.setBody("Votre réclamation a été résolue");
        notification.setType("CLAIM_RESOLVED");
        notification.setReferenceId(claimId);
        
        notificationRepository.save(notification);
    }
}
