package platform.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import platform.dtos.LevelTestDto;
import platform.entities.LevelTest;
import platform.entities.LevelTestResult;
import platform.repositories.LevelTestRepository;
import platform.repositories.LevelTestResultRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LevelTestService {
    private final LevelTestRepository levelTestRepository;
    private final LevelService levelService;

    public List<LevelTest> getLevelTests() {
        return levelTestRepository.findAllByOrderByLevelIdAscNumAsc();
    }

    public List<LevelTestDto> getLevelTestsDto() {
        List<LevelTest> tests = getLevelTests();
        return tests.stream().map(LevelTestDto::getInstance).toList();
    }


    @Transactional
    public List<LevelTestDto> createLevelTest(LevelTestDto testDto) {
        int num = testDto.getNum();
        if(num<0){
            return null;
        }
        List<LevelTest> tests = getLevelTests();
        if (num < tests.size()) {
            int offset = 0;
            for (int i = 0; i < tests.size(); i++) {
                LevelTest test = tests.get(i);
                if (test.getNum() == num) {
                    offset = 1;
                }
                int shouldNumber = i + 1 + offset;
                if (test.getNum() != shouldNumber) {
                    test.setNum(shouldNumber);
                    levelTestRepository.save(test);
                }
            }
        }
        LevelTest test = new LevelTest();
        test.setNum(num);
        test.setQuestion("");
        test.setLevel(levelService.getLevelByStartedName(testDto.getLevel(), true));
        System.out.println("before saving test: " + test);
        levelTestRepository.save(test);
        return getLevelTestsDto();
    }

    @Transactional
    public LevelTestDto updateTest(LevelTestDto testDto) {
        LevelTest test = levelTestRepository.findById(testDto.getId()).orElseThrow();
        testDto.updateLevelTest(test, levelService.getLevelByStartedName(testDto.getLevel(), false));
        return LevelTestDto.getInstance(levelTestRepository.save(test));
    }

    @Transactional
    public List<LevelTestDto> deleteTest(LevelTestDto testDto) {
        int number = 1;
        for (LevelTest test : getLevelTests()) {
            if (test.getId().equals(testDto.getId())) {
                levelTestRepository.deleteById(testDto.getId());
                continue;
            }
            if (test.getNum() != number) {
                test.setNum(number);
                levelTestRepository.save(test);
            }
            number++;
        }
        return getLevelTestsDto();
    }

    public ResponseEntity<?> changeOrderLevelTest(LevelTestDto testDto) {
        LevelTest currentTest = levelTestRepository.findById(testDto.getId()).orElseThrow();
        LevelTest otherTest = levelTestRepository.findFirstByNum(testDto.getNum()).orElseThrow();

        otherTest.setNum(currentTest.getNum());
        currentTest.setNum(testDto.getNum());
        levelTestRepository.save(currentTest, otherTest);

        return ResponseEntity.ok().build();
    }
}
