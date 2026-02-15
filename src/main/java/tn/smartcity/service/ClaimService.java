package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.smartcity.dto.request.CreateClaimRequest;
import tn.smartcity.dto.response.CategoryResponse;
import tn.smartcity.dto.response.ClaimResponse;
import tn.smartcity.dto.response.UserResponse;
import tn.smartcity.exception.ResourceNotFoundException;
import tn.smartcity.model.Category;
import tn.smartcity.model.Claim;
import tn.smartcity.model.User;
import tn.smartcity.model.enums.ClaimStatus;
import tn.smartcity.repository.CategoryRepository;
import tn.smartcity.repository.ClaimRepository;
import tn.smartcity.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ClaimResponse createClaim(CreateClaimRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Claim claim = new Claim();
        claim.setUser(user);
        claim.setCategory(category);
        claim.setTitle(request.getTitle());
        claim.setDescription(request.getDescription());
        claim.setLatitude(request.getLatitude());
        claim.setLongitude(request.getLongitude());
        claim.setAddress(request.getAddress());
        claim.setImageUrl(request.getImageUrl());
        claim.setVoiceTranscription(request.getVoiceTranscription());
        claim.setStatus(ClaimStatus.EN_ATTENTE);

        claim = claimRepository.save(claim);

        return mapToClaimResponse(claim);
    }

    public List<ClaimResponse> getMyClaimsResponse(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return claimRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToClaimResponse)
                .collect(Collectors.toList());
    }

    public List<ClaimResponse> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(this::mapToClaimResponse)
                .collect(Collectors.toList());
    }

    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim", "id", id));
        return mapToClaimResponse(claim);
    }

    @Transactional
    public void deleteClaim(Long id, Long userId) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim", "id", id));

        if (!claim.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Claim", "id", id);
        }

        claimRepository.delete(claim);
    }

    private ClaimResponse mapToClaimResponse(Claim claim) {
        ClaimResponse response = new ClaimResponse();
        response.setId(claim.getId());
        response.setTitle(claim.getTitle());
        response.setDescription(claim.getDescription());
        response.setStatus(claim.getStatus().name());
        response.setPriority(claim.getPriority().name());
        response.setLatitude(claim.getLatitude());
        response.setLongitude(claim.getLongitude());
        response.setAddress(claim.getAddress());
        response.setImageUrl(claim.getImageUrl());
        response.setCreatedAt(claim.getCreatedAt());
        response.setResolvedAt(claim.getResolvedAt());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(claim.getCategory().getId());
        categoryResponse.setNameFr(claim.getCategory().getNameFr());
        categoryResponse.setNameAr(claim.getCategory().getNameAr());
        categoryResponse.setIconName(claim.getCategory().getIconName());
        categoryResponse.setColor(claim.getCategory().getColor());
        response.setCategory(categoryResponse);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(claim.getUser().getId());
        userResponse.setName(claim.getUser().getName());
        userResponse.setEmail(claim.getUser().getEmail());
        response.setUser(userResponse);

        if (claim.getAssignedAgent() != null) {
            UserResponse agentResponse = new UserResponse();
            agentResponse.setId(claim.getAssignedAgent().getId());
            agentResponse.setName(claim.getAssignedAgent().getName());
            response.setAssignedAgent(agentResponse);
        }

        return response;
    }
}
