package platform.dtos;

import lombok.Data;
import platform.entities.Unit;
import platform.entities.MenuItem;
import platform.entities.MenuType;

@Data
public class MenuItemDto {
    private Long id;
    private String name;
    private int order;
    private String url;
    private String img;
    private String title;
    private Long unitId;

    public static MenuItemDto getInstance(MenuItem item) {
        MenuItemDto itemDto = new MenuItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setOrder(item.getOrder());
        itemDto.setUrl(item.getUrl());
        itemDto.setImg(item.getImg());
        itemDto.setTitle(item.getTitle());
        if(item.getUnit() != null) {
            itemDto.setUnitId(item.getUnit().getId());
        }
        return itemDto;
    }
    public void updateMenuItem(MenuItem item, MenuType type, Unit lesson) {
        item.setType(type);
        item.setName(getName());
        item.setOrder(getOrder());
        item.setUrl(getUrl());
        item.setImg(getImg());
        item.setTitle(getTitle());
        item.setUnit(lesson);
    }
}
