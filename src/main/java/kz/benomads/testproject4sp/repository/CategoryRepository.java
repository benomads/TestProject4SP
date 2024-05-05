package kz.benomads.testproject4sp.repository;

import kz.benomads.testproject4sp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCategoryName(String categoryName);
}
