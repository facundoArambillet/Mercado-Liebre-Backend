package com.mercado_liebre.product_service.model.productImage;

import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {

    private Long idProductImage;
    private byte[] image;
    private ProductDTO product;
}
