package org.tomei.assessment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductOrderDto {
    private String orderId;
    private Integer productId;
    private String title;
    private Integer quantity;
    private Double totalPrice;
}
