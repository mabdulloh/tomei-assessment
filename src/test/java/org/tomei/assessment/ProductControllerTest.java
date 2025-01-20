package org.tomei.assessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.controller.ProductController;
import org.tomei.assessment.dto.ProductDto;
import org.tomei.assessment.service.ProductService;
import org.tomei.assessment.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    private ProductController productController;
    @Mock
    private ExternalProductService externalProductService;

    @BeforeEach
    void setUp() {
        final ProductService productService = new ProductServiceImpl(externalProductService);
        productController = new ProductController(productService);
    }

    @Test
    void testFetchProducts() {
        when(externalProductService.fetchProducts())
                .thenReturn(fetchProducts());
        final var result = productController.fetchProducts();
        final var resultBody = result.getBody();
        assertNotNull(resultBody);
        assertEquals(9, resultBody.size());
    }

    @Test
    void testFindProduct() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(findProductById());
        final var result = productController.fetchProduct(1);
        final var resultBody = result.getBody();
        assertNotNull(resultBody);
    }

    private static ProductDto findProductById() {
        return new ProductDto()
                .setId(1);
    }

    private static List<ProductDto> fetchProducts() {
        int i = 1;
        List<ProductDto> products = new ArrayList<>();
        do {
            var productDto = new ProductDto();
            productDto.setId(i);
            products.add(productDto);
            i++;
        } while (i < 10);
        return products;
    }
}
