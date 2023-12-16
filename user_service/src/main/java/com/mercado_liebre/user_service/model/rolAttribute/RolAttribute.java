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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false, foreignKey = @ForeignKey(name = "fk_rol_attributes_user_roles",
            foreignKeyDefinition = "FOREIGN KEY (id_rol) REFERENCES user_roles(id_rol) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private UserRol userRol;


}
