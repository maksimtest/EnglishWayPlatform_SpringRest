package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import platform.dtos.MenuItemDto;
import platform.entities.*;
import platform.repositories.UnitRepository;
import platform.repositories.MenuItemRepository;
import platform.repositories.MenuTypeRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final UnitRepository unitRepository;
    private final MenuTypeRepository menuTypeRepository;

    public Optional<MenuItem> findById(Long id) {
        return menuItemRepository.findById(id);
    }
    public ResponseEntity<?> deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    public List<MenuItem> getMenuByType(List<Role> roles, MenuType type) {
        Set<MenuItem> set = new HashSet<>();
        roles.forEach(r -> set.addAll(menuItemRepository.findByTypeAndRole(type, r)));

        List<MenuItem> menu = new ArrayList<>(set);
        menu.sort(Comparator.comparingLong(MenuItem::getOrder));
        return menu;
    }
    public List<MenuItemDto> updateMenuItemForLesson(List<MenuItemDto> menuItemDtoList){
        if(menuItemDtoList==null || menuItemDtoList.isEmpty()){
            return menuItemDtoList;
        }

        Long lessonId = menuItemDtoList.get(0).getUnitId();
        List<MenuItem> items = menuItemRepository.findByUnitId(lessonId);
        // step 1: delete from DB unreferenced menuItem instance
        for(MenuItem item : items){
            if(menuItemDtoList
                    .stream().allMatch(item1 -> item1.getId().equals(item.getId()))){
                menuItemRepository.deleteById(item.getId());
            }
        }
        // step 2: update
        MenuType type = menuTypeRepository.getLessonMenuType();
        Unit lesson = unitRepository.findById(lessonId).orElseThrow();
        for(MenuItemDto itemDto: menuItemDtoList){
            MenuItem item = items
                    .stream()
                    .filter(item1 -> item1.getId().equals(itemDto.getId()))
                    .findFirst()
                    .orElse(new MenuItem());
            itemDto.updateMenuItem(item, type, lesson);
            menuItemRepository.save(item);
        }
        return getMenuItemsByLesson(lessonId);
    }
    public List<MenuItemDto> getMenuItemsByLesson(Long lessonId) {
        List<MenuItem> items = menuItemRepository.findByUnitId(lessonId);
        if (items == null || items.isEmpty()) {
            return createGroupMenuItemByLesson(lessonId);
        }
        return items.stream().map(MenuItemDto::getInstance).toList();
    }

    public MenuItemDto createMenuItemsForLesson(MenuItemDto menuItemDto) {
        Long lessonId = menuItemDto.getUnitId();
        MenuItem menuItem = new MenuItem();
        MenuType type = menuTypeRepository.getLessonMenuType();
        Unit lesson = unitRepository.findById(lessonId).orElseThrow();
        menuItemDto.updateMenuItem(menuItem, type, lesson);

        menuItem = menuItemRepository.save(menuItem);
        return MenuItemDto.getInstance(menuItem);
    }

    public List<MenuItemDto> createGroupMenuItemByLesson(Long lessonId) {
        List<MenuItemDto> resultMenuItemDtoList = new ArrayList<>();
        MenuType type = menuTypeRepository.getLessonMenuType();
        Unit lesson = unitRepository.findById(lessonId).orElseThrow();

        String[] names = new String[]{"Main", "Homework", "Grammar"};
        int i = 0;

        for (String name : names) {
            MenuItem menuItem = new MenuItem();
            menuItem.setUnit(lesson);
            menuItem.setName(name);
            menuItem.setOrder(++i);
            menuItem.setType(type);
            menuItem = menuItemRepository.save(menuItem);
            resultMenuItemDtoList.add(MenuItemDto.getInstance(menuItem));
        }
        return resultMenuItemDtoList;
    }
}
