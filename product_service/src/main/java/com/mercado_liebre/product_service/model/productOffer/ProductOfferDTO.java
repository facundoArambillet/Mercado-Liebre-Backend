package com.mercado_liebre.product_service.model.productOffer;

import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOfferDTO {
    private Long idProductOffer;
    private double discountPercentage;
    private double total;
    private ProductDTO product;
}
