package tn.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.smartcity.model.Claim;
import tn.smartcity.model.User;
import tn.smartcity.model.enums.ClaimStatus;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    
    List<Claim> findByUser(User user);
    
    List<Claim> findByStatus(ClaimStatus status);
    
    List<Claim> findByAssignedAgent(User agent);
    
    List<Claim> findByUserOrderByCreatedAtDesc(User user);
    
    List<Claim> findByStatusOrderByCreatedAtDesc(ClaimStatus status);
    
    @Query("SELECT c FROM Claim c WHERE c.status = :status AND c.assignedAgent IS NULL")
    List<Claim> findUnassignedClaimsByStatus(@Param("status") ClaimStatus status);
    
    @Query("SELECT c FROM Claim c WHERE " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(c.latitude)) * " +
           "cos(radians(c.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(c.latitude)))) <= :radiusKm")
    List<Claim> findNearby(@Param("latitude") BigDecimal latitude,
                           @Param("longitude") BigDecimal longitude,
                           @Param("radiusKm") Double radiusKm);
    
    Long countByStatus(ClaimStatus status);
    
    Long countByUser(User user);
}
