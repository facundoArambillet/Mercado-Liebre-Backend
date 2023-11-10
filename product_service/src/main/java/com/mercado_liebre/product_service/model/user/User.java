package com.mercado_liebre.product_service.model.user;

import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.userRol.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    private String email;
    private String password;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "sales_made")
    private Long salesMade;

    @ManyToOne
    @JoinColumn(name= "id_rol")
    private UserRol userRol;
    @ManyToMany(mappedBy = "users")
    private Set<Product> products;

}
