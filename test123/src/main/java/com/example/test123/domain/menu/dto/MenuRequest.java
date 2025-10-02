package com.example.test123.domain.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private String menuPicture;
    private String menuContent;
    private Long categoryId;
    private Boolean available;
    private Integer stockQuantity;
}
