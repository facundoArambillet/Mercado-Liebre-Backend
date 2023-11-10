package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferDTO;

import java.util.List;
import java.util.Optional;

public interface ProductOfferService {

    List<ProductOfferDTO> getAll();
    Optional<ProductOfferDTO> getById(Long id);
    ProductOffer createProductOffer(ProductOffer productOffer);
    ProductOfferDTO updateProductOffer(Long id, ProductOfferDTO productOfferDTO);
    ProductOfferDTO deleteProductOffer(Long id);
}
