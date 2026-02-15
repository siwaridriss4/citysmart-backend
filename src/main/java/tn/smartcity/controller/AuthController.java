package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.request.LoginRequest;
import tn.smartcity.dto.request.OTPRequest;
import tn.smartcity.dto.request.RegisterRequest;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.dto.response.AuthResponse;
import tn.smartcity.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API d'authentification")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Inscription réussie", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie", response));
    }

    @PostMapping("/otp/send")
    @Operation(summary = "Envoyer un code OTP pour le mode invité")
    public ResponseEntity<ApiResponse<Void>> sendOTP(
            @Valid @RequestBody OTPRequest request) {
        
        // TODO: Implémenter l'envoi d'OTP
        return ResponseEntity.ok(ApiResponse.success("Code OTP envoyé"));
    }

    @PostMapping("/otp/verify")
    @Operation(summary = "Vérifier le code OTP et se connecter en mode invité")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOTP(
            @Valid @RequestBody OTPRequest request) {
        
        AuthResponse response = authService.loginAsGuest(request);
        return ResponseEntity.ok(ApiResponse.success("Connexion invité réussie", response));
    }
}
