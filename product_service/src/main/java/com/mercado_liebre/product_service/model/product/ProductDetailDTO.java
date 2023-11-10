package com.mercado_liebre.product_service.model.product;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentPlanDTO;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.model.user.User;
import com.mercado_liebre.product_service.model.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Long idProduct;
    private String name;
    private double price;
    private int stock;
    private String description;
    private boolean isWeeklyOffer;
    private CategoryDTO category;
    private UserDTO user;
    private List<ProductAttributeDTO> productAttributes;
    private List<PaymentPlanDTO> paymentPlans;
}
