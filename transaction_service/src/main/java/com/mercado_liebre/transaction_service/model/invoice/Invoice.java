package com.mercado_liebre.transaction_service.model.invoice;

import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private double total;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private ShoppingCart shoppingCart;
}
