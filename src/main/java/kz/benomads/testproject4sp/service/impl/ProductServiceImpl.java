package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.repository.CategoryRepository;
import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.exception.NullValueException;
import kz.benomads.testproject4sp.exception.ProductNotFoundException;
import kz.benomads.testproject4sp.exception.UserNotFoundException;
import kz.benomads.testproject4sp.mapper.ProductDtoMapper;
import kz.benomads.testproject4sp.repository.ProductRepository;
import kz.benomads.testproject4sp.dto.ProductDto;
import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductDtoMapper productDtoMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, Long userId) {
        if (productDto == null) {
            throw new NullValueException("ProductDto cannot be null");
        }
        // Check if the required fields are null or empty
        if (productDto.getTitle() == null || productDto.getTitle().isEmpty()) {
            throw new NullValueException("Product name cannot be null or empty");
        }
        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            throw new NullValueException("Product description cannot be null or empty");
        }
        if (productDto.getPrice() == null) {
            throw new NullValueException("Product price cannot be null");
        }
        // Check if the user exists
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(
                String.format("UserEntity id=%d not found", userId)));

        Long categoryId = productDto.getCategory().get(0).getId();

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ProductNotFoundException(
                String.format("Category id=%d not found", categoryId)));

// Build and save the Product
        Product product = Product.builder()
            .title(productDto.getTitle())
            .description(productDto.getDescription())
            .price(productDto.getPrice())
            .quantity(productDto.getQuantity())
            .imageUrl(productDto.getImageUrl())
            .category(Collections.singletonList(category))
            .build();
        productRepository.save(product);

        // Add the product to the user's list of products
        user.getProducts().add(product);
        userRepository.save(user);

        return productDtoMapper.apply(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        if (id == null) {
            throw new NullValueException("Product id cannot be null");
        }
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(
                String.format("Product id=%d not found", id)));

        return productDtoMapper.apply(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        return products.stream()
            .map(productDtoMapper)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Category category) {
        if (category == null || category.toString().isEmpty()) {
            throw new NullValueException("Category cannot be null or empty");
        }

        List<Product> products = productRepository.findAllProductsByCategory(category);

        return products.stream()
            .map(productDtoMapper)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        if (productDto == null || id == null) {
            throw new NullValueException("ProductDto or Product Id cannot be null");
        }

        Product product = productRepository.findById(productDto.getId())
            .orElseThrow(() -> new ProductNotFoundException(
                String.format("Product id=%d not found", id)));

        Product checkedProduct = checkProductDtoFields(product, productDto);

        productRepository.save(checkedProduct);

        return productDtoMapper.apply(checkedProduct);
    }

    private Product checkProductDtoFields(Product product, ProductDto productDto) {
        if (productDto.getTitle() != null && !productDto.getTitle().isEmpty()) {
            product.setTitle(productDto.getTitle());
        }
        if (productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getImageUrl() != null && !productDto.getImageUrl().isEmpty()) {
            product.setImageUrl(productDto.getImageUrl());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }
        if (productDto.getQuantity() != null) {
            product.setQuantity(productDto.getQuantity());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new NullValueException("Product id cannot be null");
        }
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(
                String.format("Product id=%d not found", id));
        }
        productRepository.deleteById(id);

    }
}
