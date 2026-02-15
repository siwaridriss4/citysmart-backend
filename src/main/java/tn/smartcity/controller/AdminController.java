package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.model.enums.ClaimStatus;
import tn.smartcity.repository.ClaimRepository;
import tn.smartcity.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin", description = "API pour les administrateurs")
public class AdminController {

    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;

    @GetMapping("/stats")
    @Operation(summary = "Récupérer les statistiques globales")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userRepository.count());
        stats.put("totalClaims", claimRepository.count());
        stats.put("pendingClaims", claimRepository.countByStatus(ClaimStatus.EN_ATTENTE));
        stats.put("inProgressClaims", claimRepository.countByStatus(ClaimStatus.EN_COURS));
        stats.put("resolvedClaims", claimRepository.countByStatus(ClaimStatus.RESOLU));
        stats.put("rejectedClaims", claimRepository.countByStatus(ClaimStatus.REJETE));
        
        return ResponseEntity.ok(ApiResponse.success("Statistiques récupérées", stats));
    }

    @GetMapping("/users")
    @Operation(summary = "Récupérer tous les utilisateurs")
    public ResponseEntity<ApiResponse<Long>> getTotalUsers() {
        Long count = userRepository.count();
        return ResponseEntity.ok(ApiResponse.success("Total utilisateurs", count));
    }
}
