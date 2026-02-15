package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.request.CreateClaimRequest;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.dto.response.ClaimResponse;
import tn.smartcity.exception.ResourceNotFoundException;
import tn.smartcity.model.User;
import tn.smartcity.repository.UserRepository;
import tn.smartcity.service.ClaimService;
import tn.smartcity.service.GamificationService;
import tn.smartcity.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Claims", description = "API de gestion des réclamations")
public class ClaimController {

    private final ClaimService claimService;
    private final UserRepository userRepository;
    private final GamificationService gamificationService;
    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Créer une nouvelle réclamation")
    public ResponseEntity<ApiResponse<ClaimResponse>> createClaim(
            @Valid @RequestBody CreateClaimRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        ClaimResponse response = claimService.createClaim(request, user.getId());
        
        // Gamification
        gamificationService.addPointsForClaim(user, 10);
        
        // Notification
        notificationService.sendClaimCreatedNotification(user, response.getId());
        
        return ResponseEntity.ok(ApiResponse.success("Réclamation créée avec succès", response));
    }

    @GetMapping("/my")
    @Operation(summary = "Récupérer mes réclamations")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getMyClaims(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<ClaimResponse> claims = claimService.getMyClaimsResponse(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Réclamations récupérées", claims));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une réclamation par ID")
    public ResponseEntity<ApiResponse<ClaimResponse>> getClaimById(@PathVariable Long id) {
        ClaimResponse claim = claimService.getClaimById(id);
        return ResponseEntity.ok(ApiResponse.success("Réclamation trouvée", claim));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une réclamation")
    public ResponseEntity<ApiResponse<Void>> deleteClaim(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        claimService.deleteClaim(id, user.getId());
        return ResponseEntity.ok(ApiResponse.success("Réclamation supprimée"));
    }
}
