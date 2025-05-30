package platform.dtos;

import lombok.Data;
import platform.entities.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class LessonContentsDto {
    private Long activeMenuId;
    private List<MenuItemDto> menuItems;
    private List<ContentDto> contents = new ArrayList<>();
    private String lessonName;
}
