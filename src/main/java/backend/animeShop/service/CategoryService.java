package backend.animeShop.service;

import backend.animeShop.dto.CategoryDTO;
import backend.animeShop.model.Category;
import backend.animeShop.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());

            Category updatedCategory = categoryRepository.save(category);
            return convertToDTO(updatedCategory);
        }

        return null;
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Nuevo: obtener o crear una categoría por nombre
    public Category findOrCreateCategoryByName(String name) {
        if (name == null) {
            return null;
        }
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            return category;
        }
        Category newCat = new Category();
        newCat.setName(name);
        newCat.setDescription(null);
        return categoryRepository.save(newCat);
    }

    // Convertir entidad a DTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
