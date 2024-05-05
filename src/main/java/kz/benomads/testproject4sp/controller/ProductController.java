package kz.benomads.testproject4sp.controller;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.dto.ProductRequestDto;
import kz.benomads.testproject4sp.dto.ProductResponseDto;
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


    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
        @PathVariable List<CategoryDto> category) {

        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ProductResponseDto> createProduct(
        @RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto product = productService.createProduct(productRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRequestDto productRequestDto) {

        return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
