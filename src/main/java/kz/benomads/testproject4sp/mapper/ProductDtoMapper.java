package kz.benomads.testproject4sp.mapper;

import kz.benomads.testproject4sp.dto.ProductResponseDto;
import kz.benomads.testproject4sp.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDtoMapper implements Function<Product, ProductResponseDto> {

    @Override
    public ProductResponseDto apply(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getImageUrl(),
            product.getCategory(),
            product.getUsers().getId()
        );
    }
}
