package platform.dtos;

import lombok.Data;
import platform.entities.MenuItem;

import java.util.List;

@Data
public class CabinetDto {
    private List<MenuItemDto> mainMenu;
    private String roles;
    private String redirect;
}
