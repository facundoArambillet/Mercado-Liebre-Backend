package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import com.mercado_liebre.transaction_service.model.invoice.InvoiceDTO;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<InvoiceDTO> getAll();
    Optional<InvoiceDTO> getById(Long id);
    Invoice createInvoice(Invoice invoice);
//    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);
    InvoiceDTO deleteInvoice(Long id);
}
