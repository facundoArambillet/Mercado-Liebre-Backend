package com.mercado_liebre.user_service.model.categoryFamily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFamilyDTO {
    private Long idType;
    private String type;
    private byte[] image;
}
