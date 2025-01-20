package org.tomei.assessment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductResultDto {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    private RatingDto rating;
    private String status;
}
