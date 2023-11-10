package com.mercado_liebre.user_service.repository;

import com.mercado_liebre.user_service.model.userRol.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolRepository extends JpaRepository<UserRol,Long> {
    Optional<UserRol> findByType(String type);

}
