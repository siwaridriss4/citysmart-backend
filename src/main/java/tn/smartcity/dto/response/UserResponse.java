package tn.smartcity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private Integer points;
    private Boolean isGuest;
    private List<String> badges;
}
