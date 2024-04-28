package kz.benomads.testproject4sp.mapper;

import kz.benomads.testproject4sp.dto.ProductDto;
import kz.benomads.testproject4sp.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDtoMapper implements Function<Product, ProductDto> {

    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
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
