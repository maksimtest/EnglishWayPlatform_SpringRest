package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.MenuItemDto;
import platform.dtos.ParamIdDto;
import platform.services.MenuItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminMenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping("admin/get-menu-items-by-unit")
    public List<MenuItemDto> createMenuItemByUnit(@RequestBody ParamIdDto paramId) {
        System.out.println("AdminMenuItemController(admin/get-menu-items-by-unit), containerIdDto=" + paramId);
        List<MenuItemDto> list = menuItemService.getMenuItemsByLesson(paramId.getId());
        System.out.println("AdminMenuItemController(admin/get-menu-items-by-lesson) return list=" + list);
        return list;
    }

    @PostMapping("admin/create-menu-item-for-unit")
    public MenuItemDto createForLesson(@RequestBody MenuItemDto menuItemDto) {
        System.out.println("AdminMenuItemController(create-menu-item-for-unit) post menuItemDto=" + menuItemDto);
        MenuItemDto resultMenuItemDto = menuItemService.createMenuItemsForLesson(menuItemDto);
        System.out.println("AdminMenuItemController(create-menu-item-for-unit) return resultMenuItemDto=" + resultMenuItemDto);
        return resultMenuItemDto;
    }

    @PostMapping("admin/update-menu-items-for-unit")
    public List<MenuItemDto> updateMenuItemForUnit(@RequestBody List<MenuItemDto> menuItemDtoList) {
        System.out.println("AdminMenuItemController(update-menu-item-for-unit) post menuItemDtoList=" + menuItemDtoList);
        List<MenuItemDto> result = menuItemService.updateMenuItemForLesson(menuItemDtoList);
        System.out.println("AdminMenuItemController(update-menu-item-for-lesson) return result=" + result);
        return result;
    }
    @PostMapping("admin/delete-menu-item")
    public ResponseEntity<?> deleteMenuItem(@RequestBody MenuItemDto menuItemDto) {
        System.out.println("AdminMenuItemController(delete-menu-item) post menuItemDto.getId()="+menuItemDto.getId());
        ResponseEntity<?> result = menuItemService.deleteMenuItem(menuItemDto.getId());
        System.out.println("AdminMenuItemController(delete-menu-item) return result="+result);
        return result;
    }
}
