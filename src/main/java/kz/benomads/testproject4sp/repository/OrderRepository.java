package kz.benomads.testproject4sp.repository;

import kz.benomads.testproject4sp.dto.CategoryDto;
import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByProductCategory(CategoryDto product_category);

    List<Order> findAll();

    Optional<Order> findOrderById(Long id);

    List<Order> findAllByUserId(Long userId);
}
