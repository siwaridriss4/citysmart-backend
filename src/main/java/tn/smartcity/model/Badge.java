package tn.smartcity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "badges")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(name = "name_fr", nullable = false, length = 100)
    private String nameFr;

    @Column(name = "name_ar", nullable = false, length = 100)
    private String nameAr;

    @Column(name = "description_fr", columnDefinition = "TEXT")
    private String descriptionFr;

    @Column(name = "description_ar", columnDefinition = "TEXT")
    private String descriptionAr;

    @Column(name = "icon_name", length = 50)
    private String iconName;

    @Column(length = 7)
    private String color;

    @Column(name = "requirement_type", nullable = false)
    private String requirementType;

    @Column(name = "requirement_value", nullable = false)
    private Integer requirementValue;

    @Column(name = "points_reward")
    private Integer pointsReward = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
