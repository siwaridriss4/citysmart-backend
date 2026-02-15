package tn.smartcity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimResponse {
    
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String imageUrl;
    private CategoryResponse category;
    private UserResponse user;
    private UserResponse assignedAgent;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}
