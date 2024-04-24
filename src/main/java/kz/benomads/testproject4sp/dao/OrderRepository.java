package kz.benomads.testproject4sp.dao;

import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByProductCategory(Category product_category);
}
