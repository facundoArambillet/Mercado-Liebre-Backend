package com.mercado_liebre.transaction_service.repository;

import com.mercado_liebre.transaction_service.model.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT i FROM Invoice i WHERE i.shoppingCart.idCart = :idCart")
    List<Invoice> findInvoicesByIdCart(@Param("idCart") Long idCart);

}
