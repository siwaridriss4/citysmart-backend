package tn.smartcity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.smartcity.model.enums.ClaimPriority;
import tn.smartcity.model.enums.ClaimStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status = ClaimStatus.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimPriority priority = ClaimPriority.MOYENNE;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "voice_transcription", columnDefinition = "TEXT")
    private String voiceTranscription;

    @Column(name = "ai_detected_category", length = 100)
    private String aiDetectedCategory;

    @Column(name = "ai_confidence", precision = 3, scale = 2)
    private BigDecimal aiConfidence;

    @Column(name = "ai_moderation_flag")
    private Boolean aiModerationFlag = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id")
    private User assignedAgent;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "is_duplicate")
    private Boolean isDuplicate = false;

    @Column(name = "citizen_rating")
    private Integer citizenRating;

    @Column(name = "citizen_feedback", columnDefinition = "TEXT")
    private String citizenFeedback;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isPending() {
        return this.status == ClaimStatus.EN_ATTENTE;
    }

    public boolean isInProgress() {
        return this.status == ClaimStatus.EN_COURS;
    }

    public boolean isResolved() {
        return this.status == ClaimStatus.RESOLU;
    }

    public boolean isUrgent() {
        return this.priority == ClaimPriority.URGENTE;
    }

    public void assignToAgent(User agent) {
        if (agent != null && agent.isAgent()) {
            this.assignedAgent = agent;
            this.status = ClaimStatus.EN_COURS;
        }
    }

    public void resolve() {
        this.status = ClaimStatus.RESOLU;
        this.resolvedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = ClaimStatus.REJETE;
    }
}
