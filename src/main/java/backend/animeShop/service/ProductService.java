package backend.animeShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.animeShop.dto.ProductDTO;
import backend.animeShop.model.Product;
import backend.animeShop.model.Category;
import backend.animeShop.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProducts(String query) {
        return productRepository.searchProducts(query).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO createProduct(Product product) {
        // Auto-assign category if none provided
        if (product.getCategory() == null) {
            Category assigned = determineCategoryForProduct(product);
            product.setCategory(assigned);
        }
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    private Category determineCategoryForProduct(Product product) {
        String name = product.getName() != null ? product.getName().toLowerCase() : "";
        String description = product.getDescription() != null ? product.getDescription().toLowerCase() : "";

        // Regla simple basada en palabras clave para las categorias permitidas
        if (containsAny(name, description, "ropa", "shirt", "camiseta", "pantalon", "pants")) {
            return categoryService.findOrCreateCategoryByName("ropa");
        }
        if (containsAny(name, description, "accesorio", "bolso", "gafas", "accesorios")) {
            return categoryService.findOrCreateCategoryByName("accesorios");
        }
        if (containsAny(name, description, "figura", "figurine")) {
            return categoryService.findOrCreateCategoryByName("figuras");
        }
        if (containsAny(name, description, "carta", "card", "cartas")) {
            return categoryService.findOrCreateCategoryByName("cartas");
        }
        if (containsAny(name, description, "juego", "board", "game")) {
            return categoryService.findOrCreateCategoryByName("juegos");
        }

        // Default fallback si no hay coincidencias
        return categoryService.findOrCreateCategoryByName("accesorios");
    }

    private boolean containsAny(String a, String b, String... keywords) {
        for (String kw : keywords) {
            if ((a != null && a.contains(kw)) || (b != null && b.contains(kw))) {
                return true;
            }
        }
        return false;
    }

    public ProductDTO updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            product.setImageUrl(productDetails.getImageUrl());
            product.setFeatured(productDetails.getFeatured());
            //product.setCategory(productDetails.getCategory());
            
            Product updatedProduct = productRepository.save(product);
            return convertToDTO(updatedProduct);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        dto.setRating(product.getRating());
        dto.setFeatured(product.getFeatured());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : "");
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }
}
