package com.mercado_liebre.transaction_service.model.invoice;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper mapper = Mappers.getMapper(InvoiceMapper.class);

    InvoiceDTO invoiceToInvoiceDto(Invoice invoice);

    Invoice invoiceDtoToInvoice(InvoiceDTO invoiceDTO);
}
