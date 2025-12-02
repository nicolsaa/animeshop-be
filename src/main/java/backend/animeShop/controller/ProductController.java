package backend.animeShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import backend.animeShop.dto.ProductDTO;
import backend.animeShop.model.Product;
import backend.animeShop.model.Category;
import backend.animeShop.repository.CategoryRepository;
import backend.animeShop.service.ProductService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;


    
    @GetMapping //trae todos los productos
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}") //trae los productos por id
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}") // filtra por categoria
    public List<ProductDTO> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/featured") // productos destacados
    public List<ProductDTO> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }

    @GetMapping("/search") // busca productos
    public List<ProductDTO> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

@PostMapping
public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO req) {
        try {
            Category category = categoryRepository.findByName(req.getCategoryName());

            Product product = new Product(
                    req.getName(),
                    req.getDescription(),
                    req.getPrice(),
                    req.getStock(),
                    category
            );

            ProductDTO newProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")  //modifica el producto
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")  // borra el producto
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
