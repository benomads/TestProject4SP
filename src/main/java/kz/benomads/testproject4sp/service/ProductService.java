package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.ProductDto;
import kz.benomads.testproject4sp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDto createProduct(ProductDto productDto, Long userId);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(Category category);

    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}
