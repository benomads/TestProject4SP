package kz.benomads.testproject4sp.controller;

import kz.benomads.testproject4sp.dto.ProductDto;
import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //TODO: Implement the rest of the CRUD operations for the Product entity

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(
        @PathVariable Category category) {

        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ProductDto> createProduct(
        @PathVariable Long userId,
        @RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto,userId);


        return ResponseEntity.status(HttpStatus.CREATED)
            .body(product);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDto productDto) {

        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
