package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.error.ResponseException;
import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import com.mercado_liebre.transaction_service.model.invoice.InvoiceDTO;
import com.mercado_liebre.transaction_service.model.invoice.InvoiceMapper;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.repository.InvoiceRepository;
import com.mercado_liebre.transaction_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<InvoiceDTO> getAll() {
        try {
            List<Invoice> invoices = invoiceRepository.findAll();
            List<InvoiceDTO> invoiceDTOS = invoices.stream().map(
                    invoice -> InvoiceMapper.mapper.invoiceToInvoiceDto(invoice)).collect(Collectors.toList());

            return invoiceDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<InvoiceDTO> getById(Long idInvoice) {
        try {
            Optional<Invoice> invoiceFound = invoiceRepository.findById(idInvoice);
            if (invoiceFound.isPresent()) {
                Invoice invoice = invoiceFound.get();
                InvoiceDTO invoiceDTO = InvoiceMapper.mapper.invoiceToInvoiceDto(invoice);

                return Optional.ofNullable(invoiceDTO);
            } else {
                throw new ResponseException("Invoice not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching Invoice", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  List<InvoiceDTO> getInvoicesByIdCart(Long idShoppingCart) {
        try {
            Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
            if(shoppingCartFound.isPresent()) {
                List<Invoice> invoicesFound = invoiceRepository.findInvoicesByIdCart(idShoppingCart);
                List<InvoiceDTO> invoiceDTOS = invoicesFound.stream().map(
                        invoice -> InvoiceMapper.mapper.invoiceToInvoiceDto(invoice)).collect(Collectors.toList());

                return invoiceDTOS;
            } else {
                throw new ResponseException("Shopping cart does not exist", null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Email Invoice", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Invoice createInvoice(Invoice invoice) {
        try {
            Long idShoppingCart = invoice.getShoppingCart().getIdCart();
            Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
            if(shoppingCartFound.isPresent()) {
                return invoiceRepository.save(invoice);
            } else {
                throw new ResponseException("Shopping cart does not exist", null, HttpStatus.BAD_REQUEST);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create Invoice", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public InvoiceDTO updateInvoice(Long idInvoice, InvoiceDTO invoiceDTO) {
        try {
            Optional<Invoice> invoiceFound = invoiceRepository.findById(idInvoice);
            if(invoiceFound.isPresent()) {
                Long idShoppingCart = invoiceDTO.getShoppingCart().getIdCart();
                Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
                if(shoppingCartFound.isPresent()) {
                    Invoice invoiceUpdated = invoiceFound.get();
                    invoiceUpdated.setTotal(invoiceDTO.getTotal());
                    invoiceUpdated.setDate(invoiceDTO.getDate());
                    invoiceRepository.save(invoiceUpdated);

                    InvoiceDTO invoiceDTOUpdated = InvoiceMapper.mapper.invoiceToInvoiceDto(invoiceUpdated);
                    return invoiceDTOUpdated;
                } else {
                    throw new ResponseException("Shopping cart does not exist", null, HttpStatus.NOT_FOUND);
                }

            } else {
                throw new ResponseException("That Invoice does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update Invoice", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public InvoiceDTO deleteInvoice(Long idInvoice) {
        try {
            Optional<Invoice> invoiceFound = invoiceRepository.findById(idInvoice);
            if(invoiceFound.isPresent()) {
                Invoice invoiceDelete = invoiceFound.get();
                InvoiceDTO invoiceDTO = InvoiceMapper.mapper.invoiceToInvoiceDto(invoiceDelete);
                invoiceRepository.delete(invoiceDelete);

                return invoiceDTO;
            } else {
                throw new ResponseException("That Invoice does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete InvoiceRol", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
