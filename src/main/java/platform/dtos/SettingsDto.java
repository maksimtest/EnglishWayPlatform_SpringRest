package platform.dtos;

import lombok.Data;
import platform.entities.Settings;

@Data
public class SettingsDto {
    private Long id;
    private String code;
    private String description;
    private boolean value;

    public static SettingsDto getInstance(Settings settings) {
        SettingsDto dto = new SettingsDto();
        dto.setId(settings.getId());
        dto.setCode(settings.getCode());
        dto.setDescription(settings.getDescription());
        dto.setValue(settings.isValue());
        return dto;
    }
}
