package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import platform.dtos.LevelTestDto;
import platform.services.LevelTestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LevelTestController {
    private final LevelTestService levelTestService;

    // TODO ADD DTO
    // TODO ADD security check. only SuperContentManager
    @PostMapping("/auth/level-tests")
    public List<LevelTestDto> getLevelTests() {
        System.out.println("LevelTestController(level-tests)");
        List<LevelTestDto> list = levelTestService.getLevelTestsDto();
        System.out.println("LevelTestController(level-tests) list="+list);
        return list;
    }
//    @PostMapping("/admin/level-test")
//    public List<LevelTest> getAdminLevelTest() {
//        return levelTestService.getLevelTests();
//    }

}
