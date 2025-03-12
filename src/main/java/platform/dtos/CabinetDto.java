package platform.dtos;

import lombok.Data;
import platform.entities.Menu;
import platform.entities.MenuItem;

import java.util.List;

@Data
public class CabinetDto {
    private Menu mainMenu;
    private Menu asideMenu;

}
