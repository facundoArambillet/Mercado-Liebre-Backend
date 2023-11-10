package com.mercado_liebre.transaction_service.model.invoice;

import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long idInvoice;
    private double total;
    private Date date;
    private ShoppingCart shoppingCart;
}
