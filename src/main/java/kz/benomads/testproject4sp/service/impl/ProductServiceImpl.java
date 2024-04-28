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
        validateInput(productDto, userId);

        UserEntity user = findUserById(userId);

        List<Category> validCategories = checkCategories(productDto.getCategory());

        Product product = buildProduct(productDto, user, validCategories);

        Product savedProduct = saveProduct(product);

        addUserProduct(user, savedProduct);

        return productDtoMapper.apply(savedProduct);
    }

    private void validateInput(ProductDto productDto, Long userId) {
        if (productDto == null || userId == null) {
            throw new NullValueException("ProductDto or User Id cannot be null");
        }
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(
                String.format("User id=%d not found", userId)));
    }

    private List<Category> checkCategories(List<Category> categories) {
        List<Category> validCategories = categories.stream()
            .map(category -> categoryRepository.findAllByCategoryName(category.getCategoryName()))
            .flatMap(List::stream)
            .collect(Collectors.toList());

        if (validCategories.isEmpty()) {
            throw new ProductNotFoundException("No valid categories found");
        }
        return validCategories;
    }

    private Product buildProduct(ProductDto productDto, UserEntity user, List<Category> validCategories) {
        return Product.builder()
            .title(productDto.getTitle())
            .description(productDto.getDescription())
            .imageUrl(productDto.getImageUrl())
            .category(validCategories)
            .quantity(productDto.getQuantity())
            .price(productDto.getPrice())
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
    public ProductDto getProductById(Long id) {
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

    private ProductDto mapProductToDto(Product product) {
        return productDtoMapper.apply(product);
    }



    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        return mapProductsToDto(products);
    }

    private List<ProductDto> mapProductsToDto(List<Product> products) {
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

        Product checkedProduct = checkProductDtoFieldsForUpdate(product, productDto);

        productRepository.save(checkedProduct);

        return productDtoMapper.apply(checkedProduct);
    }

    private Product checkProductDtoFieldsForUpdate(Product product, ProductDto productDto) {
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
