package com.everybox.everybox.dto;

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
    private String category;
    private Integer quantity;
    private String imageUrl;
    private Boolean isClosed;
}
