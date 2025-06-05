package platform.dtos;

import lombok.Data;
import platform.entities.Unit;

import java.util.List;

@Data
public class UnitDto {
    private Long id;
    private String name;
    private int num;
    private List<MenuItemDto> menuItems;

    public static UnitDto getInstance(Unit unit) {
        UnitDto dto = new UnitDto();
        dto.setId(unit.getId());
        dto.setName(unit.getName());
        dto.setNum(unit.getNum());
        return dto;
    }

    public void updateUnit(Unit unit){
        unit.setName(getName());
        unit.setNum(getNum());
    }
}
