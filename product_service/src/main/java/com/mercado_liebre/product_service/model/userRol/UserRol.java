package com.mercado_liebre.product_service.model.userRol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(nullable = false)
    private String type;

}
