package org.tomei.assessment.service;

import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.dto.ProductResultDto;

import java.util.List;

public interface ProductService {
    List<ProductResultDto> fetchProducts();
    ProductResultDto findByProductId(Integer productId);
}
