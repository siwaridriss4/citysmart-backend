package tn.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.smartcity.model.Badge;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    
    Optional<Badge> findByCode(String code);
    
    List<Badge> findByIsActive(Boolean isActive);
    
    List<Badge> findByRequirementType(String requirementType);
}
