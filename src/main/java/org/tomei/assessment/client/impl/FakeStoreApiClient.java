package org.tomei.assessment.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.dto.ProductDto;
import org.tomei.assessment.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class FakeStoreApiClient implements ExternalProductService {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "https://fakestoreapi.com";

    @Override
    public List<ProductDto> fetchProducts() {
        final var productUrl = BASE_URL + "/products";
        return Arrays.asList(Objects.requireNonNull(REST_TEMPLATE.getForObject(productUrl, ProductDto[].class)));
    }

    @Override
    public Optional<ProductDto> findByProductId(Integer productId) {
        return fetchProducts()
                .stream()
                .filter(x -> x.getId().equals(productId))
                .findAny();
    }
}
