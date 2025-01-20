package org.tomei.assessment.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    private RatingDto rating;
}
