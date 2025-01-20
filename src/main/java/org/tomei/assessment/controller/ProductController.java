package org.tomei.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tomei.assessment.dto.ProductResultDto;
import org.tomei.assessment.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static final String BASE_ROUTE = "/products";

    @GetMapping(BASE_ROUTE)
    public List<ProductResultDto> fetchProducts() {
        return productService.fetchProducts();
    }

    @GetMapping(BASE_ROUTE + "/{productId}")
    public ProductResultDto fetchProduct(@PathVariable String productId) {
        return productService.findByProductId(Integer.parseInt(productId));
    }
}
