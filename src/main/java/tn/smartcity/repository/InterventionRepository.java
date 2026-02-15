package tn.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.smartcity.model.Intervention;
import tn.smartcity.model.User;

import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    
    List<Intervention> findByAgent(User agent);
    
    List<Intervention> findByAgentOrderByCreatedAtDesc(User agent);
    
    List<Intervention> findByStatus(String status);
}
