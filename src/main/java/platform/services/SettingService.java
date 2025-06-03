package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dtos.SettingsDto;
import platform.entities.Settings;
import platform.repositories.SettingsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingsRepository settingsRepository;

    public List<SettingsDto> getSettings(){
        List<Settings> settings = settingsRepository.findAllByOrderByIdAsc();
        List<SettingsDto> settingDtos = settings
                .stream()
                .map(SettingsDto::getInstance)
                .toList();
        return settingDtos;
    }

    public SettingsDto updateSettings(SettingsDto settingsDto){
        Settings settings = settingsRepository.findById(settingsDto.getId()).orElseThrow();
        settings.setDescription(settingsDto.getDescription());
        settings.setValue(settingsDto.isValue());
        settings = settingsRepository.save(settings);
        return SettingsDto.getInstance(settings);
    }
}
