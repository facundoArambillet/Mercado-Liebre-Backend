package com.mercado_liebre.user_service.model.rolAttribute;

import com.mercado_liebre.user_service.model.userRol.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribute")
    private Long idAttribute;
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private UserRol userRol;


}
