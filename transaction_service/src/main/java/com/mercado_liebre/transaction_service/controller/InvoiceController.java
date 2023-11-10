package com.mercado_liebre.transaction_service.controller;

import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import com.mercado_liebre.transaction_service.model.invoice.InvoiceDTO;
import com.mercado_liebre.transaction_service.service.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceServiceImpl invoiceService;
    @GetMapping
    public List<InvoiceDTO> getAll() {
        return invoiceService.getAll();
    }
    @GetMapping("/{idInvoice}")
    public Optional<InvoiceDTO> getById(@PathVariable("idInvoice") Long idInvoice) {
        return invoiceService.getById(idInvoice);
    }
    @GetMapping("/cart/{idCart}")
    public List<InvoiceDTO> getInvoicesByIdCart(@PathVariable("idCart") Long idCart) {
        return invoiceService.getInvoicesByIdCart(idCart);
    }
    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }
    @PutMapping("/{idInvoice}")
    public InvoiceDTO updateInvoice(@PathVariable("idInvoice")Long idInvoice, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.updateInvoice(idInvoice, invoiceDTO);
    }
    @DeleteMapping("/{idInvoice}")
    public InvoiceDTO deleteInvoice(@PathVariable("idInvoice") Long idInvoice) {
        return invoiceService.deleteInvoice(idInvoice);
    }
}
