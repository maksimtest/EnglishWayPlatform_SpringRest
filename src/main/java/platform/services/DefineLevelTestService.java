package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.datas.MarkLevelTest;
import platform.entities.Level;
import platform.entities.LevelTest;
import platform.entities.LevelTestResult;
import platform.repositories.LevelRepository;
import platform.repositories.LevelTestRepository;
import platform.utils.StringUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DefineLevelTestService {
    private final LevelRepository levelRepository;
    private final LevelTestService levelTestService;

    public LevelTestResult getLevelTestResultByAnswers(String answers) {
        List<MarkLevelTest> marks = getSortedMarks(answers);
        System.out.println("DefineLevelTestResult: marks=" + marks);
        LevelTestResult result = new LevelTestResult();

        result.setAnswers(answers);
        updateRecommendation(result, marks);
        updateLevel(result, marks);
        updateScore(result, marks);
        return result;
    }

    private void updateLevel(LevelTestResult result, List<MarkLevelTest> marks) {
        result.setLevel(levelRepository.getFirst());
        for (MarkLevelTest mark : marks) {
            System.out.println("updateLevel: mark correct=" + mark.getCorrect()+", count="+mark.getCount());
            if (mark.getCount() > 0 && 100 * mark.getCorrect() / mark.getCount() < 80) {
                result.setLevel(mark.getLevel());
                return;
            }
        }
    }

    private List<MarkLevelTest> getSortedMarks(String answersString) {
        List<LevelTest> tests = levelTestService.getLevelTests();
        Map<Long, MarkLevelTest> marksByLevels = new HashMap<>();
        List<Level> levels = levelRepository.findAllByOrderByCodeAsc();
        for (Level level : levels) {
            marksByLevels.put(level.getId(), MarkLevelTest.getInstance(level));
        }
        List<Integer> answers = StringUtil.getMarksByStr(answersString);
        for (int i = 0; i < tests.size(); i++) {
            LevelTest test = tests.get(i);
            MarkLevelTest mark = marksByLevels.get(test.getLevel().getId());
            mark.setCount(mark.getCount() + 1);
            if (i < answers.size()) {
                if (answers.get(i) == test.getCorrect()) {
                    mark.setCorrect(mark.getCorrect() + 1);
                }
            }
        }
        List<MarkLevelTest> marks = new ArrayList<>(marksByLevels.values());
        marks.sort(Comparator.comparingLong(MarkLevelTest::getId));
        return marks;
    }

    private void updateRecommendation(LevelTestResult result, List<MarkLevelTest> marks) {
        marks.sort(Comparator.comparingLong(MarkLevelTest::getId));
        StringBuilder str = new StringBuilder("Levels: ");
        String separator = "";
        for (MarkLevelTest mark : marks) {
            str.append(separator)
                    .append(mark.getLevel().getCode()).append(" ")
                    .append(mark.getCorrect()).append("/").append(mark.getCount());
            separator = ", ";
        }
        result.setRecommendation(str.toString());
    }

    private void updateScore(LevelTestResult result, List<MarkLevelTest> marks) {
        int score = marks.stream().mapToInt(MarkLevelTest::getCorrect).sum();
        int count = marks.stream().mapToInt(MarkLevelTest::getCount).sum();
        result.setScore(score * 100 / count);
    }
}
