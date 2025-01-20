package org.tomei.assessment.util;

import lombok.experimental.UtilityClass;
import org.tomei.assessment.dto.ProductDto;
import org.tomei.assessment.dto.ProductResultDto;

@UtilityClass
public class ProductMapper {

    public ProductResultDto mapToProductResultDto(final ProductDto productDto) {
        return new ProductResultDto()
                .setId(productDto.getId())
                .setTitle(productDto.getTitle())
                .setPrice(productDto.getPrice())
                .setCategory(productDto.getCategory())
                .setRating(productDto.getRating())
                .setDescription(productDto.getDescription())
                .setStatus(ProductUtil.determineStatus(productDto.getId()));
    }
}
