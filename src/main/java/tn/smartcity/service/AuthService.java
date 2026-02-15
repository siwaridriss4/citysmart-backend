package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.smartcity.dto.request.LoginRequest;
import tn.smartcity.dto.request.OTPRequest;
import tn.smartcity.dto.request.RegisterRequest;
import tn.smartcity.dto.response.AuthResponse;
import tn.smartcity.dto.response.UserResponse;
import tn.smartcity.exception.UnauthorizedException;
import tn.smartcity.model.User;
import tn.smartcity.model.enums.UserRole;
import tn.smartcity.repository.UserRepository;
import tn.smartcity.security.JwtTokenProvider;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UnauthorizedException("Cet email est déjà utilisé");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new UnauthorizedException("Ce numéro de téléphone est déjà utilisé");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CITOYEN);
        user.setIsActive(true);
        user.setIsGuest(false);

        user = userRepository.save(user);

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(user.getRole().name())
                .build();

        String token = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return new AuthResponse(token, refreshToken, mapToUserResponse(user));
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Email ou mot de passe incorrect"));

        if (!user.getIsActive()) {
            throw new UnauthorizedException("Compte désactivé");
        }

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(user.getRole().name())
                .build();

        String token = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return new AuthResponse(token, refreshToken, mapToUserResponse(user));
    }

    @Transactional
    public AuthResponse loginAsGuest(OTPRequest request) {
        // TODO: Vérifier le code OTP
        
        User user = userRepository.findByPhone(request.getPhone())
                .orElseGet(() -> {
                    User newGuest = new User();
                    newGuest.setName("Invité");
                    newGuest.setPhone(request.getPhone());
                    newGuest.setRole(UserRole.CITOYEN);
                    newGuest.setIsActive(true);
                    newGuest.setIsGuest(true);
                    newGuest.setPhoneVerified(true);
                    return userRepository.save(newGuest);
                });

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getPhone())
                .password("")
                .authorities(user.getRole().name())
                .build();

        String token = jwtTokenProvider.generateToken(userDetails);

        return new AuthResponse(token, mapToUserResponse(user));
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().name());
        response.setPoints(user.getPoints());
        response.setIsGuest(user.getIsGuest());
        response.setBadges(new ArrayList<>());
        return response;
    }
}
