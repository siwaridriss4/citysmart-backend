package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.dto.response.ClaimResponse;
import tn.smartcity.service.ClaimService;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('AGENT', 'ADMIN')")
@Tag(name = "Agent", description = "API pour les agents municipaux")
public class AgentController {

    private final ClaimService claimService;

    @GetMapping("/claims")
    @Operation(summary = "Récupérer toutes les réclamations (pour agents)")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getAllClaims() {
        List<ClaimResponse> claims = claimService.getAllClaims();
        return ResponseEntity.ok(ApiResponse.success("Réclamations récupérées", claims));
    }

    @PutMapping("/claims/{id}/assign")
    @Operation(summary = "Assigner une réclamation à un agent")
    public ResponseEntity<ApiResponse<Void>> assignClaim(
            @PathVariable Long id,
            @RequestParam Long agentId) {
        
        // TODO: Implémenter l'assignation
        return ResponseEntity.ok(ApiResponse.success("Réclamation assignée"));
    }

    @PutMapping("/claims/{id}/resolve")
    @Operation(summary = "Marquer une réclamation comme résolue")
    public ResponseEntity<ApiResponse<Void>> resolveClaim(@PathVariable Long id) {
        // TODO: Implémenter la résolution
        return ResponseEntity.ok(ApiResponse.success("Réclamation résolue"));
    }
}
