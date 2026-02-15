package tn.smartcity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.smartcity.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByIsActive(Boolean isActive);
    
    List<Category> findByIsActiveOrderByPriorityLevelDesc(Boolean isActive);
}
