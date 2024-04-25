package kz.benomads.testproject4sp.dao;

import kz.benomads.testproject4sp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    Optional<User> findAllByUsername(String username);


    User findUserById(Long userId);
    void deleteUserById(Long userId);
}
