package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.dto.ProductRequestDto;
import kz.benomads.testproject4sp.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    ProductResponseDto getProductById(Long id);
    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> getProductsByCategory(List<CategoryDto> category);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);
    void deleteProduct(Long id);
}
