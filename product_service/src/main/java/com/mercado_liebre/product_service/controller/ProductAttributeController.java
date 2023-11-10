package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.service.ProductAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-attribute")
public class ProductAttributeController {
    @Autowired
    private ProductAttributeServiceImpl productAttributeService;

    @GetMapping
    public List<ProductAttributeDTO> getAll() {
        return productAttributeService.getAll();
    }

    @GetMapping("/{idProductAttribute}")
    public Optional<ProductAttributeDTO> getById(@PathVariable("idProductAttribute") Long idProductAttribute) {
        return productAttributeService.getById(idProductAttribute);
    }
    @PostMapping
    public ProductAttribute createProductAttribute(@RequestBody ProductAttribute productAttribute) {
        return productAttributeService.createProductAttribute(productAttribute);
    }
    @PutMapping("/{idProductAttribute}")
    public ProductAttributeDTO updateProductAttribute(@PathVariable("idProductAttribute")Long idProductAttribute,
                                                      @RequestBody ProductAttributeDTO productAttributeDTO) {
        return productAttributeService.updateProductAttribute(idProductAttribute, productAttributeDTO);
    }
    @DeleteMapping("/{idProductAttribute}")
    public ProductAttributeDTO deleteProductAttribute(@PathVariable("idProductAttribute") Long idProductAttribute) {
        return productAttributeService.deleteProductAttribute(idProductAttribute);
    }
}
