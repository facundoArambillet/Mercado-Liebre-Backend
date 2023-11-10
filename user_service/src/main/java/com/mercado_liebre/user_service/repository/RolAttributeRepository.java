package com.mercado_liebre.user_service.repository;

import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolAttributeRepository extends JpaRepository<RolAttribute,Long> {
    Optional<RolAttribute> findByName(String name);
}
