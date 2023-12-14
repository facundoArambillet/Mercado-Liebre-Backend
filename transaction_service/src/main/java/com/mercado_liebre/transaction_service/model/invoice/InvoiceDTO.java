package com.mercado_liebre.transaction_service.model.invoice;

import com.mercado_liebre.transaction_service.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long idInvoice;
    private double total;
    private Date date;
    private List<Product> products;
}
