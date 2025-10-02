package com.example.test123.domain.menu.service;

import com.example.test123.domain.menu.dto.MenuRequest;
import com.example.test123.domain.menu.dto.MenuResponse;
import com.example.test123.domain.menu.entity.Category;
import com.example.test123.domain.menu.entity.Menu;
import com.example.test123.domain.menu.repository.CategoryRepository;
import com.example.test123.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public MenuResponse createMenu(MenuRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());
        menu.setMenuPicture(request.getMenuPicture());
        menu.setMenuContent(request.getMenuContent());
        menu.setCategory(category);
        menu.setAvailable(request.getAvailable() != null ? request.getAvailable() : true);
        menu.setStockQuantity(request.getStockQuantity());

        Menu savedMenu = menuRepository.save(menu);
        return MenuResponse.from(savedMenu);
    }

    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuRequest request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));

        if (request.getName() != null) {
            menu.setName(request.getName());
        }
        if (request.getPrice() != null) {
            menu.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            menu.setDescription(request.getDescription());
        }
        if (request.getMenuPicture() != null) {
            menu.setMenuPicture(request.getMenuPicture());
        }
        if (request.getMenuContent() != null) {
            menu.setMenuContent(request.getMenuContent());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            menu.setCategory(category);
        }
        if (request.getAvailable() != null) {
            menu.setAvailable(request.getAvailable());
        }
        if (request.getStockQuantity() != null) {
            menu.setStockQuantity(request.getStockQuantity());
        }

        Menu updatedMenu = menuRepository.save(menu);
        return MenuResponse.from(updatedMenu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new RuntimeException("Menu not found with id: " + menuId);
        }
        menuRepository.deleteById(menuId);
    }

    public MenuResponse getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));
        return MenuResponse.from(menu);
    }

    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }

    public List<MenuResponse> getMenusByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return menuRepository.findByCategory(category).stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }

    public List<MenuResponse> getAvailableMenus() {
        return menuRepository.findByAvailable(true).stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }
}
