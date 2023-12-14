package com.mercado_liebre.transaction_service.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.userRol.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
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
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "sales_made", nullable = false)
    private Long salesMade;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Invoice> invoices;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_rol")
    private UserRol userRol;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products;

}
