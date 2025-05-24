package com.everybox.everybox.dto;

import com.everybox.everybox.domain.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequestDto {
    private String title;
    private String details;
    private String location;
    private Double lat;
    private Double lng;
    private Category category;
    private int quantity;
    private String imageUrl;
    @JsonProperty("closed")
    private boolean isClosed;
}
