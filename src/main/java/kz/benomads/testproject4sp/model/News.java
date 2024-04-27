package kz.benomads.testproject4sp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News extends BaseEntity{


    private String title;

    @Lob
    private String content;


    @ManyToOne
    private Product product;

}
