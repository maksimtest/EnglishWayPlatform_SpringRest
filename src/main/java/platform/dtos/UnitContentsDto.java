package platform.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UnitContentsDto {
    private Long activeMenuId;
    private List<MenuItemDto> menuItems;
    private List<ContentDto> contents = new ArrayList<>();
    private String unitName;
}
