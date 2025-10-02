package com.example.test123.domain.menu.controller;

import com.example.test123.domain.menu.dto.MenuRequest;
import com.example.test123.domain.menu.dto.MenuResponse;
import com.example.test123.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest request) {
        MenuResponse response = menuService.createMenu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequest request) {
        MenuResponse response = menuService.updateMenu(menuId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable Long menuId) {
        MenuResponse response = menuService.getMenu(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenus(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean available) {
        List<MenuResponse> responses;

        if (categoryId != null) {
            responses = menuService.getMenusByCategory(categoryId);
        } else if (available != null && available) {
            responses = menuService.getAvailableMenus();
        } else {
            responses = menuService.getAllMenus();
        }

        return ResponseEntity.ok(responses);
    }
}
