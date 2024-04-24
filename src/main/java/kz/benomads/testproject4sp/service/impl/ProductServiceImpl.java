package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dao.ProductRepository;
import kz.benomads.testproject4sp.dto.ProductDto;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        // Check if the required fields are null or empty
        if (productDto.getTitle() == null || productDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (productDto.getPrice() == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }

        Product product = Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImageUrl())
                .category(productDto.getCategory())
                .quantity(productDto.getQuantity())
                .price(productDto.getPrice())
                .build();
        productRepository.save(product);

        return productDto;
    }

    @Override
    public ProductDto getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return ProductDto.builder()
            .id(product.getId())
            .title(product.getTitle())
            .description(product.getDescription())
            .imageUrl(product.getImageUrl())
            .category(product.getCategory())
            .quantity(product.getQuantity())
            .price(product.getPrice())
            .build();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        if (productRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("No products found");
        }
        List<ProductDto> productDtoList = productRepository.findAll().stream()
            .map(product -> ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build())
            .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        Product product = productRepository.findById(productDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategory(productDto.getCategory());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());

        Product updatedProduct = productRepository.save(product);

        return ProductDto.builder()
            .id(updatedProduct.getId())
            .title(updatedProduct.getTitle())
            .description(updatedProduct.getDescription())
            .imageUrl(updatedProduct.getImageUrl())
            .category(updatedProduct.getCategory())
            .quantity(updatedProduct.getQuantity())
            .price(updatedProduct.getPrice())
            .build();
    }

    @Override
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);

    }
}
