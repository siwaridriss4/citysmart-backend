package tn.smartcity.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClaimRequest {
    
    @NotBlank(message = "Titre est obligatoire")
    @Size(max = 255)
    private String title;
    
    @NotBlank(message = "Description est obligatoire")
    private String description;
    
    @NotNull(message = "Catégorie est obligatoire")
    private Long categoryId;
    
    @NotNull(message = "Latitude est obligatoire")
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude est obligatoire")
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;
    
    private String address;
    
    private String imageUrl;
    
    private String voiceTranscription;
}
