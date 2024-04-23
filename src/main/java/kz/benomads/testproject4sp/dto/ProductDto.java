package kz.benomads.testproject4sp.dto;

import kz.benomads.testproject4sp.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private Category category;
    private List<Long> orderIds;
    private Long userId;

}
