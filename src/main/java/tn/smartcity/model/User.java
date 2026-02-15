package tn.smartcity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.smartcity.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CITOYEN;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(name = "is_guest")
    private Boolean isGuest = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "phone_verified")
    private Boolean phoneVerified = false;

    @Column(name = "fcm_token", length = 500)
    private String fcmToken;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Claim> claims = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_badges",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badge> badges = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addPoints(int points) {
        this.points += points;
    }

    public void addBadge(Badge badge) {
        if (!this.badges.contains(badge)) {
            this.badges.add(badge);
        }
    }

    public boolean isCitoyen() {
        return this.role == UserRole.CITOYEN;
    }

    public boolean isAgent() {
        return this.role == UserRole.AGENT;
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
}
