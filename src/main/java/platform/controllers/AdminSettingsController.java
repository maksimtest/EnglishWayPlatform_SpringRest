package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.SettingsDto;
import platform.dtos.StudentActivityDto;
import platform.services.SettingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminSettingsController {
    private final SettingService settingService;

    @PostMapping("admin/get-settings")
    public List<SettingsDto> getSettings() {
        System.out.println("AdminSettingsController(get-settings)");
        List<SettingsDto> list = settingService.getSettings();
        System.out.println("AdminSettingsController(get-settings): return list=" + list);
        return list;
    }
    @PostMapping("admin/update-settings")
    public SettingsDto updateSettings(@RequestBody SettingsDto settingsDto) {
        System.out.println("AdminSettingsController(update-settings): settingsDto=" + settingsDto);
        settingsDto = settingService.updateSettings(settingsDto);
        System.out.println("AdminSettingsController(update-settings): return settingsDto=" + settingsDto);
        return settingsDto;
    }
}
