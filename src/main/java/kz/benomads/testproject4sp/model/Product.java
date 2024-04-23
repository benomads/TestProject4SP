package kz.benomads.testproject4sp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private Category category;
    @OneToMany
    private List<Order> orders;
    @ManyToOne
    private User users;

}
