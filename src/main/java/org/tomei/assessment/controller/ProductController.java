package org.tomei.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductResultDto>> fetchProducts() {
        return ResponseEntity.ok(productService.fetchProducts());
    }

    @GetMapping(BASE_ROUTE + "/{productId}")
    public ResponseEntity<ProductResultDto> fetchProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.findByProductId(productId));
    }
}
