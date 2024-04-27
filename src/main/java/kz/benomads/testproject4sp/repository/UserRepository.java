package kz.benomads.testproject4sp.repository;

import kz.benomads.testproject4sp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
    Optional<UserEntity> findAllByUsername(String username);


    UserEntity findUserById(Long userId);
    void deleteUserById(Long userId);

    Optional<UserEntity> findByUsername(String username);
}
