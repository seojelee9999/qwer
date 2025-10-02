package com.example.test123.domain.menu.dto;

import com.example.test123.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String name;
    private BigDecimal price;
    private String description;
    private String menuPicture;
    private String menuContent;
    private Long categoryId;
    private String categoryName;
    private Boolean available;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuResponse from(Menu menu) {
        MenuResponse response = new MenuResponse();
        response.setMenuId(menu.getMenuId());
        response.setName(menu.getName());
        response.setPrice(menu.getPrice());
        response.setDescription(menu.getDescription());
        response.setMenuPicture(menu.getMenuPicture());
        response.setMenuContent(menu.getMenuContent());
        response.setCategoryId(menu.getCategory().getCategoryId());
        response.setCategoryName(menu.getCategory().getCategoryName());
        response.setAvailable(menu.getAvailable());
        response.setStockQuantity(menu.getStockQuantity());
        response.setCreatedAt(menu.getCreatedAt());
        response.setUpdatedAt(menu.getUpdatedAt());
        return response;
    }
}
