package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferDTO;
import com.mercado_liebre.product_service.service.ProductOfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-offer")
public class ProductOfferController {
    @Autowired
    private ProductOfferServiceImpl productOfferService;

    @GetMapping
    public List<ProductOfferDTO> getAll() {
        return productOfferService.getAll();
    }
    @GetMapping("/{idProductOffer}")
    public Optional<ProductOfferDTO> getById(@PathVariable("idProductOffer") Long idProductOffer) {
        return productOfferService.getById(idProductOffer);
    }
    @GetMapping("/byProduct/{idProduct}")
    public Optional<ProductOfferDTO> getByIdProduct(@PathVariable("idProduct") Long idProduct) {
        return productOfferService.getByIdProduct(idProduct);
    }
    @PostMapping
    public ProductOffer createProductOffer(@RequestBody ProductOffer productOffer) {
        return productOfferService.createProductOffer(productOffer);
    }
    @PutMapping("/{idProductOffer}")
    public ProductOfferDTO updateProductOffer(@PathVariable("idProductOffer")Long idProductOffer, @RequestBody ProductOfferDTO productOfferDTO) {
        return productOfferService.updateProductOffer(idProductOffer, productOfferDTO);
    }
    @DeleteMapping("/{idProductOffer}")
    public ProductOfferDTO deleteProductOffer(@PathVariable("idProductOffer") Long idProductOffer) {
        return productOfferService.deleteProductOffer(idProductOffer);
    }
}
