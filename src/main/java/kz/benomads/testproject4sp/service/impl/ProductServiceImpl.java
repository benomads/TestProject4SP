package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.dto.ProductRequestDto;
import kz.benomads.testproject4sp.dto.ProductResponseDto;
import kz.benomads.testproject4sp.repository.CategoryRepository;
import kz.benomads.testproject4sp.repository.UserRepository;
import kz.benomads.testproject4sp.exception.NullValueException;
import kz.benomads.testproject4sp.exception.ProductNotFoundException;
import kz.benomads.testproject4sp.exception.UserNotFoundException;
import kz.benomads.testproject4sp.mapper.ProductDtoMapper;
import kz.benomads.testproject4sp.repository.ProductRepository;
import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Product;
import kz.benomads.testproject4sp.model.UserEntity;
import kz.benomads.testproject4sp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, Long userId) {
        validateInput(productRequestDto, userId);

        UserEntity user = findUserById(userId);

        List<Category> validCategories = checkCategories(productRequestDto.getCategory());

        Product product = buildProduct(productRequestDto, user, validCategories);

        Product savedProduct = saveProduct(product);

        addUserProduct(user, savedProduct);

        return productDtoMapper.apply(savedProduct);
    }

    private void validateInput(ProductRequestDto productRequestDto, Long userId) {
        if (productRequestDto == null || userId == null) {
            throw new NullValueException("ProductResponseDto or User Id cannot be null");
        }
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(
                String.format("User id=%d not found", userId)));
    }

    private List<Category> checkCategories(List<CategoryDto> categories) {
        List<Category> validCategories = categories.stream()
            .map(category -> categoryRepository.findAllByCategoryName(category.getCategoryName()))
            .flatMap(List::stream)
            .collect(Collectors.toList());

        if (validCategories.isEmpty()) {
            throw new ProductNotFoundException("No valid categories found");
        }
        return validCategories;
    }

    private Product buildProduct(ProductRequestDto productRequestDto, UserEntity user, List<Category> validCategories) {
        return Product.builder()
            .title(productRequestDto.getTitle())
            .description(productRequestDto.getDescription())
            .imageUrl(productRequestDto.getImageUrl())
            .category(validCategories)
            .quantity(productRequestDto.getQuantity())
            .price(productRequestDto.getPrice())
            .users(user)
            .build();
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    private void addUserProduct(UserEntity user, Product product) {
        user.getProducts().add(product);
        userRepository.save(user);
    }




    @Override
    public ProductResponseDto getProductById(Long id) {
        validateProductId(id);

        Product product = findProductById(id);

        return mapProductToDto(product);
    }

    private void validateProductId(Long id) {
        if (id == null) {
            throw new NullValueException("Product id cannot be null");
        }
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(
                String.format("Product id=%d not found", id)));
    }

    private ProductResponseDto mapProductToDto(Product product) {
        return productDtoMapper.apply(product);
    }



    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        return mapProductsToDto(products);
    }

    private List<ProductResponseDto> mapProductsToDto(List<Product> products) {
        return products.stream()
            .map(productDtoMapper)
            .collect(Collectors.toList());
    }




    @Override
    public List<ProductResponseDto> getProductsByCategory(List<CategoryDto> category) {
        if (category == null || category.isEmpty()) {
            throw new NullValueException("Category cannot be null or empty");
        }

        List<Product> products = productRepository.findAllProductsByCategory(checkCategories(category));

        return products.stream()
            .map(productDtoMapper)
            .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        if (productRequestDto == null || id == null) {
            throw new NullValueException("ProductResponseDto or Product Id cannot be null");
        }

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(
                String.format("Product id=%d not found", id)));

        Product checkedProduct = checkProductDtoFieldsForUpdate(product, productRequestDto);

        productRepository.save(checkedProduct);

        return productDtoMapper.apply(checkedProduct);
    }

    private Product checkProductDtoFieldsForUpdate(Product product, ProductRequestDto productRequestDto) {
        if (productRequestDto.getTitle() != null && !productRequestDto.getTitle().isEmpty()) {
            product.setTitle(productRequestDto.getTitle());
        }
        if (productRequestDto.getDescription() != null && !productRequestDto.getDescription().isEmpty()) {
            product.setDescription(productRequestDto.getDescription());
        }
        if (productRequestDto.getImageUrl() != null && !productRequestDto.getImageUrl().isEmpty()) {
            product.setImageUrl(productRequestDto.getImageUrl());
        }
        if (productRequestDto.getCategory() != null) {
            product.setCategory(checkCategories(productRequestDto.getCategory()));
        }
        if (productRequestDto.getQuantity() != null) {
            product.setQuantity(productRequestDto.getQuantity());
        }
        if (productRequestDto.getPrice() != null) {
            product.setPrice(productRequestDto.getPrice());
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
