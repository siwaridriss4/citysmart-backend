package tn.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.smartcity.model.Notification;
import tn.smartcity.model.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    List<Notification> findByUserAndIsReadOrderByCreatedAtDesc(User user, Boolean isRead);
    
    Long countByUserAndIsRead(User user, Boolean isRead);
}
