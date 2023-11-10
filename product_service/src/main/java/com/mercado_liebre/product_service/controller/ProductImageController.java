package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.productImage.ProductImage;
import com.mercado_liebre.product_service.model.productImage.ProductImageDTO;
import com.mercado_liebre.product_service.service.ProductImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-image")
public class ProductImageController {
    @Autowired
    private ProductImageServiceImpl productImageService;

    @GetMapping
    public List<ProductImageDTO> getAll() {
        return productImageService.getAll();
    }

    @GetMapping("/{idProductImage}")
    public Optional<ProductImageDTO> getById(@PathVariable("idProductImage") Long idProductImage) {
        return productImageService.getById(idProductImage);
    }
    @PostMapping
    public ProductImage createProductImage(@RequestBody ProductImage productImage) {
        return productImageService.createProductImage(productImage);
    }
    @PutMapping("/{idProductImage}")
    public ProductImageDTO updateProductImage(@PathVariable("idProductImage")Long idProductImage, @RequestBody ProductImageDTO productImageDTO) {
        return productImageService.updateProductImage(idProductImage, productImageDTO);
    }
    @DeleteMapping("/{idProductImage}")
    public ProductImageDTO deleteProductImage(@PathVariable("idProductImage") Long idProductImage) {
        return productImageService.deleteProductImage(idProductImage);
    }
}
