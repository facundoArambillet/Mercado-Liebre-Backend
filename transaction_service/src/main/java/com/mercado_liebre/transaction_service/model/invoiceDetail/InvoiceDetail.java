package com.mercado_liebre.transaction_service.model.invoiceDetail;

import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import com.mercado_liebre.transaction_service.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

@Entity
@Table(name = "invoice_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail")
    private Long idDetail;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Double total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_invoice", nullable = false)
    private Invoice invoice;
    @JoinColumn(name = "id_product", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
