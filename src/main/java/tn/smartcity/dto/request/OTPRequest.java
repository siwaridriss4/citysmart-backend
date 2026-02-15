package tn.smartcity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPRequest {
    
    @NotBlank(message = "Téléphone est obligatoire")
    @Pattern(regexp = "^\\+216[0-9]{8}$", message = "Numéro de téléphone invalide")
    private String phone;
}
