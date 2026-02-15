package tn.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.smartcity.dto.response.ApiResponse;
import tn.smartcity.dto.response.CategoryResponse;
import tn.smartcity.model.Category;
import tn.smartcity.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API de gestion des catégories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories actives")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository.findByIsActiveOrderByPriorityLevelDesc(true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success("Catégories récupérées", categories));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par ID")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        return ResponseEntity.ok(ApiResponse.success("Catégorie trouvée", mapToResponse(category)));
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setNameFr(category.getNameFr());
        response.setNameAr(category.getNameAr());
        response.setIconName(category.getIconName());
        response.setColor(category.getColor());
        response.setPriorityLevel(category.getPriorityLevel());
        return response;
    }
}
