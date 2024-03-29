package com.aozbek.ecommerce.repository;

import com.aozbek.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
