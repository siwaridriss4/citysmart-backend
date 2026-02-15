package tn.smartcity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "Nom est obligatoire")
    @Size(min = 2, max = 255, message = "Le nom doit contenir entre 2 et 255 caractères")
    private String name;
    
    @NotBlank(message = "Email est obligatoire")
    @Email(message = "Email invalide")
    private String email;
    
    @NotBlank(message = "Téléphone est obligatoire")
    @Pattern(regexp = "^\\+216[0-9]{8}$", message = "Numéro de téléphone invalide (format: +216XXXXXXXX)")
    private String phone;
    
    @NotBlank(message = "Mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
}
