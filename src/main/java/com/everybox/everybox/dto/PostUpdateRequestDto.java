package com.everybox.everybox.dto;

import com.everybox.everybox.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequestDto {
    private String title;
    private String details;
    private String location;
    private Double lat;
    private Double lng;
    private Category category;
    private Integer quantity;
    private String imageUrl;
    private Boolean isClosed;
}
