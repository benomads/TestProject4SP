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
public class ProductResponseDto {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private List<Category> category;
    private Long usersId;

}
