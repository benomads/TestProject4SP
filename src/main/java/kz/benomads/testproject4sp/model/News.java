package kz.benomads.testproject4sp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News extends BaseEntity{


    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    private Product product;

}
