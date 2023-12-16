package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductCreateDTO;
import com.mercado_liebre.product_service.model.product.ProductDTO;
import com.mercado_liebre.product_service.model.product.ProductDetailDTO;
import com.mercado_liebre.product_service.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }
    @GetMapping("by-category-name/{categoryName}")
    public List<ProductDTO> getAllByCategoryName(@PathVariable("categoryName") String categoryName) {
        return productService.getAllByCategoryName(categoryName);
    }
    @GetMapping("/offers")
    public List<ProductDTO> getProductsInOffer() {
        return productService.getProductsInOffer();
    }
    @GetMapping("/latest-offers")
    public List<ProductDTO> getProductsInWeeklyOffer(){
        return productService.getProductsInWeeklyOffer();
    }
    @GetMapping("/{idProduct}")
    public Optional<ProductDetailDTO> getById(@PathVariable("idProduct") Long idProduct) {
        return productService.getById(idProduct);
    }
    @GetMapping("/by-category-family/{categoryFamilyType}")
    public List<ProductDTO> getProductsByTypeCategoryFamily(@PathVariable("categoryFamilyType") String categoryFamilyType) {
        return productService.getProductsByTypeCategoryFamily(categoryFamilyType);
    }
    @GetMapping("/name/{productName}")
    public Optional<ProductDetailDTO> getByName(@PathVariable("productName") String  productName) {
        return productService.getByName(productName);
    }
    @GetMapping("/history/{productName}/{idUser}")
    public  Optional<ProductDTO> getByNameAndInsertIntoHistory(@PathVariable("productName") String productName, @PathVariable("idUser") Long idUser) {
        return productService.getByNameAndInsertIntoHistory(productName,idUser);
    }
    @GetMapping("/history/latest-category/{idUser}")
    public List<ProductDetailDTO> getProductsByLatestCategoryInUserHistory(@PathVariable("idUser") Long idUser) {
        return productService.getProductsByLatestCategoryInUserHistory(idUser);
    }
    @GetMapping("/history/latest/{idUser}")
    public List<ProductDetailDTO> getLatestProductsInHistoryByIdUser(@PathVariable("idUser") Long idUser) {
        return productService.getLatestProductsInHistoryByIdUser(idUser);
    }
    @GetMapping("/by-cart/{idCart}")
    public List<ProductDetailDTO> getProductsInShoppingCart(@PathVariable("idCart") Long idCart) {
        return productService.getProductsInShoppingCart(idCart);
    }
    @PostMapping
    public ProductCreateDTO createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        return productService.createProduct(productCreateDTO);
    }
    @PostMapping("/attribute/{idProduct}/{idAttribute}")
    public ProductDTO insertProductAttribute(@PathVariable("idProduct") Long idProduct, @PathVariable("idAttribute") Long idAttribute) {
        return productService.insertProductAttribute(idProduct,idAttribute);
    }
    @PostMapping("/payment-plan/{idProduct}/{idPayment}")
    public ProductDTO insertPaymentPlan(@PathVariable("idProduct") Long idProduct, @PathVariable("idPayment") Long idPayment) {
        return productService.insertPaymentPlan(idProduct, idPayment);
    }
    @PutMapping("/{idProduct}")
    public ProductDetailDTO updateProduct(@PathVariable("idProduct")Long idProduct, @RequestBody ProductDetailDTO productDetailDTO) {
        return productService.updateProduct(idProduct, productDetailDTO);
    }
    @DeleteMapping("/payment-plan/{idProduct}/{idPayment}")
    public ProductDTO removePaymentPlan(@PathVariable("idProduct") Long idProduct, @PathVariable("idPayment") Long idPayment) {
        return productService.removePaymentPlan(idProduct, idPayment);
    }
    @DeleteMapping("/{idProduct}")
    public ProductDTO deleteProduct(@PathVariable("idProduct") Long idProduct) {
        return productService.deleteProduct(idProduct);
    }
}
