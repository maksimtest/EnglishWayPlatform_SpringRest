package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.MenuItemDto;
import platform.entities.MenuItem;
import platform.repositories.MenuItemRepository;
import platform.services.MenuItemService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemService menuItemService;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @PostMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/admin/catalog")
    public String adminCatalogData() {
        return "Admin catalog data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}