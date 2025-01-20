package org.tomei.assessment.client;

import org.tomei.assessment.dto.ProductDto;

import java.util.List;

public interface ExternalProductService {
    List<ProductDto> fetchProducts();
    ProductDto findByProductId(Integer productId);
}
