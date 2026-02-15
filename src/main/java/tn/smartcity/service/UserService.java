package tn.smartcity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.smartcity.dto.response.UserResponse;
import tn.smartcity.exception.ResourceNotFoundException;
import tn.smartcity.model.User;
import tn.smartcity.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    
    private final UserRepository userRepository;

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        return mapToUserResponse(user);
    }

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return mapToUserResponse(user);
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
