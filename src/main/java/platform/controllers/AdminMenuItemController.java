package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.ContainerIdDto;
import platform.dtos.LessonMenuDto;
import platform.dtos.MenuItemDto;
import platform.services.MenuItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminMenuItemController {
    private final MenuItemService menuItemService;

    //    @PostMapping("get-contents")
//    public List<ContentDto> getContents(@RequestBody ContentDto contentDto) {
//        System.out.println("AdminContentController(get-contents): post contentDto="+contentDto);
//        List<ContentDto> list = contentService.getContents(contentDto);
//        System.out.println("AdminContentController(get-contents): list="+list);
//        return list;
//    }
    @PostMapping("admin/get-menu-items-by-lesson")
    public List<MenuItemDto> createMenuItemByLesson(@RequestBody ContainerIdDto containerIdDto) {
        System.out.println("AdminMenuItemController(admin/get-menu-items-by-lesson), containerIdDto=" + containerIdDto);
        List<MenuItemDto> list = menuItemService.getMenuItemsByLesson(containerIdDto.getId());
        ;
        System.out.println("AdminMenuItemController(admin/get-menu-items-by-lesson) return list=" + list);
        return list;
    }

    @PostMapping("admin/create-menu-item-for-lesson")
    public MenuItemDto createForLesson(@RequestBody MenuItemDto menuItemDto) {
        System.out.println("AdminMenuItemController(create-menu-item-for-lesson) post menuItemDto=" + menuItemDto);
        MenuItemDto resultMenuItemDto = menuItemService.createMenuItemsForLesson(menuItemDto);
        System.out.println("AdminMenuItemController(create-menu-item-for-lesson) return resultMenuItemDto=" + resultMenuItemDto);
        return resultMenuItemDto;
    }

    @PostMapping("admin/update-menu-items-for-lesson")
    public List<MenuItemDto> updateMenuItemForLesson(@RequestBody List<MenuItemDto> menuItemDtoList) {
        System.out.println("AdminMenuItemController(update-menu-item-for-lesson) post menuItemDtoList=" + menuItemDtoList);
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
