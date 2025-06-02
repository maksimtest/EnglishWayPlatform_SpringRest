package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dtos.LevelTestDto;
import platform.services.LevelTestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminLevelTestController {
    private final LevelTestService levelTestService;

    @PostMapping("admin/get-level-tests")
    public List<LevelTestDto> getLevelTests() {
        System.out.println("AdminLevelTestController(edit-level-test): post");
        List<LevelTestDto> list = levelTestService.getLevelTestsDto();
        System.out.println("AdminLevelTestController(edit-level-test): list="+list);
        return list;
    }
    @PostMapping("admin/create-level-test")
    public List<LevelTestDto> createLevelTest(@RequestBody LevelTestDto testDto) {
        System.out.println("AdminLevelTestController(create-level-test), testDto="+testDto);
        List<LevelTestDto> list = levelTestService.createLevelTest(testDto);
        System.out.println("AdminLevelTestController(edit-level-test) return list="+list);
        return list;
    }

    @PostMapping("admin/update-level-test")
    public LevelTestDto saveLevelTest(@RequestBody LevelTestDto testDto) {
        System.out.println("AdminLevelTestController(update-level-test) post LevelTestDto="+testDto);
        LevelTestDto test = levelTestService.updateTest(testDto);
        System.out.println("AdminLevelTestController(update-level-test) return test="+test);
        return test;
    }
    @PostMapping("admin/change-order-level-test")
    public ResponseEntity<?> changeOrderForLevelTests(@RequestBody LevelTestDto testDto) {
        System.out.println("AdminLevelTestController(change-order-save-level-test) post LevelTestDto="+testDto);
        ResponseEntity<?> result = levelTestService.changeOrderLevelTest(testDto);
        System.out.println("AdminLevelTestController(change-order-save-level-test) return result="+result);
        return result;
    }
    @PostMapping("admin/delete-level-test")
    public List<LevelTestDto> deleteLevelTest(@RequestBody LevelTestDto testDto) {
        System.out.println("AdminLevelTestController(delete-level-test) post LevelTestDto="+testDto);
        List<LevelTestDto> list = levelTestService.deleteTest(testDto);
        System.out.println("AdminLevelTestController(delete-level-test) return list="+list);
        return list;
    }
}
