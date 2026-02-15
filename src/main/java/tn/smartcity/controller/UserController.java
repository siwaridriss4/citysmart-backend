package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.dto.response.UserResponse;
import tn.smartcity.exception.ResourceNotFoundException;
import tn.smartcity.model.User;
import tn.smartcity.repository.UserRepository;
import tn.smartcity.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "API de gestion des utilisateurs")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    @Operation(summary = "Récupérer mon profil")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        UserResponse response = userService.getUserProfile(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Profil récupéré", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur trouvé", user));
    }
}
