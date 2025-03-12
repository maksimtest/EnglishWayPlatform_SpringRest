package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.dtos.CabinetDto;
import platform.entities.Menu;
import platform.entities.MenuItem;
import platform.entities.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinetService {
    private static Menu mainMenu = new Menu();
    private static Menu asideMenu = new Menu();

    private final RoleService roleService;
    private final UserService userService;

    public void init(){
        MenuItem item1 = new MenuItem("Home", "#", "images/home-icon.png","");
        MenuItem item2 = new MenuItem("Home2", "#", "images/users-icon.png","");
        MenuItem item3 = new MenuItem("Home3", "#", "images/download-icon.png","");
        MenuItem item4 = new MenuItem("Home4", "#", "images/download-icon.png","");
        mainMenu.setItems(List.of(item1, item2, item3, item4));
        asideMenu.setItems(List.of(item1, item2, item3, item4));
    }
    public CabinetDto getCabinet(String username){
        System.out.println("CabinetService.getCabinet, username: " + username);
        User user = userService.findByUsername(username).orElse(new User());
        System.out.println("\tCabinetService.getCabinet, user: " + user.getName());

        init();
        CabinetDto cabinetDto = new CabinetDto();
        cabinetDto.setMainMenu(mainMenu);
        cabinetDto.setAsideMenu(asideMenu);
        return cabinetDto;
    }
}
