package org.tomei.assessment.client;

import org.tomei.assessment.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ExternalProductService {
    List<ProductDto> fetchProducts();
    Optional<ProductDto> findByProductId(Integer productId);
}
