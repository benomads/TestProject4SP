package kz.benomads.testproject4sp.repository;

import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Product findProductById(Long productId);

    List<Product> findAllProductsByCategory(Category category);
}
