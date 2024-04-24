package kz.benomads.testproject4sp.service;

import kz.benomads.testproject4sp.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(Long id);
}
