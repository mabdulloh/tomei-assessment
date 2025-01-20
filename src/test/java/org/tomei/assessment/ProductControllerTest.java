package org.tomei.assessment;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.controller.OrderController;
import org.tomei.assessment.controller.ProductController;
import org.tomei.assessment.dto.ProductDto;
import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.dto.RatingDto;
import org.tomei.assessment.exception.NotFoundException;
import org.tomei.assessment.exception.OutOfStockException;
import org.tomei.assessment.exception.ValidationException;
import org.tomei.assessment.service.OrderService;
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

    private ProductController productController;
    private OrderController orderController;
    @Mock
    private ExternalProductService externalProductService;

    @BeforeEach
    void setUp() {
        final ProductService productService = new ProductServiceImpl(externalProductService);
        final OrderService orderService = new ProductServiceImpl(externalProductService);
        productController = new ProductController(productService);
        orderController = new OrderController(orderService);
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

    @Test
    void testPlaceOrder() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(Optional.of(findProductToOrder()));
        final var productOrder = new ProductOrderDto()
                .setProductId(2)
                .setQuantity(20);
        var items = orderController.fetchOrders();
        assertTrue(items.isEmpty());
        final var result = orderController.placeOrder(productOrder);
        items = orderController.fetchOrders();
        assertEquals(1, items.size());
        assertEquals(result.getOrderId(), items.get(0).getOrderId());
        assertEquals(result.getTotalPrice(), items.get(0).getTotalPrice());
    }

    @Test
    void testPlaceOrder_notFound() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(Optional.empty());
        final var productOrder = new ProductOrderDto()
                .setProductId(2)
                .setQuantity(20);
        final var exception = assertThrows(NotFoundException.class, () -> {
            orderController.placeOrder(productOrder);
        });
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test
    void testPlaceOrder_outOfStock() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(Optional.of(findProductById()));
        final var productOrder = new ProductOrderDto()
                .setProductId(1)
                .setQuantity(20);
        final var exception = assertThrows(OutOfStockException.class, () -> {
            orderController.placeOrder(productOrder);
        });
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("is out of stock"));
    }

    @Test
    void testPlaceOrder_invalidQty() {
        when(externalProductService.findByProductId(anyInt()))
                .thenReturn(Optional.of(findProductToOrder()));
        final var productOrder = new ProductOrderDto()
                .setProductId(2)
                .setQuantity(0);
        final var exception = assertThrows(ValidationException.class, () -> {
            orderController.placeOrder(productOrder);
        });
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("can not be lower than 1"));
    }

    private static ProductDto findProductById() {
        return new ProductDto()
                .setId(1)
                .setPrice(10D)
                .setTitle(RandomString.make(20))
                .setDescription(RandomString.make(300))
                .setImage(RandomString.make(100))
                .setCategory(RandomString.make(20))
                .setRating(new RatingDto(5.00, 100));
    }

    private static ProductDto findProductToOrder() {
        return new ProductDto()
                .setId(2)
                .setPrice(10D)
                .setTitle(RandomString.make(20))
                .setDescription(RandomString.make(300))
                .setImage(RandomString.make(100))
                .setCategory(RandomString.make(20))
                .setRating(new RatingDto(5.00, 100));
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
