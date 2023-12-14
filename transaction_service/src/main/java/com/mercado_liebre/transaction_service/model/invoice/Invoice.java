package com.mercado_liebre.transaction_service.model.invoice;

import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long idInvoice;
    @Column(nullable = false)
    private double total;
    @Column(nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "invoices_has_products",
            joinColumns = @JoinColumn(name = "id_invoice"),
            inverseJoinColumns = @JoinColumn(name = "id_product"),
            foreignKey = @ForeignKey(name = "fk_invoices_has_products_invoices", foreignKeyDefinition = "FOREIGN KEY (id_invoice) REFERENCES invoices(id_invoice) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey  = @ForeignKey(name = "fk_invoices_has_products_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE")

    )
    private List<Product> products;
}
