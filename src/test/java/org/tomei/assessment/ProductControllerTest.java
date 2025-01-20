package org.tomei.assessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.controller.ProductController;
import org.tomei.assessment.dto.ProductDto;
import org.tomei.assessment.exception.NotFoundException;
import org.tomei.assessment.service.ProductService;
import org.tomei.assessment.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ProductControllerTest.class);
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
                .thenReturn(Optional.of(findProductById()));
        final var result = productController.fetchProduct(1);
        final var resultBody = result.getBody();
        assertNotNull(resultBody);
    }

    @Test
    void testFindProduct_notFound() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(Optional.empty());
        final var exception = assertThrows(NotFoundException.class, () -> {
            productController.fetchProduct(1);
        });
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Product not found"));
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
