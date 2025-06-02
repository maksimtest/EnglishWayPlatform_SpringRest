package platform.dtos;

import lombok.Data;
import platform.entities.Level;
import platform.entities.LevelTest;

@Data
public class LevelTestDto {
    private Long id;
    private int num;
    private String question;
    private String case1;
    private String case2;
    private String case3;
    private int correct;
    private String level;

    public static LevelTestDto getInstance(LevelTest test) {
        LevelTestDto testDto = new LevelTestDto();
        testDto.setId(test.getId());
        testDto.setNum(test.getNum());
        testDto.setQuestion(test.getQuestion());
        testDto.setCase1(test.getCase1());
        testDto.setCase2(test.getCase2());
        testDto.setCase3(test.getCase3());
        testDto.setCorrect(test.getCorrect());
        testDto.setLevel(test.getLevel().getName()+"("+test.getLevel().getCode()+")");
        return testDto;
    }
    public void updateLevelTest(LevelTest test, Level level){
        test.setId(id);
        test.setQuestion(question);
        test.setCase1(case1);
        test.setCase2(case2);
        test.setCase3(case3);
        test.setCorrect(correct);
        if(level != null) {
            test.setLevel(level);
        }
    }
}
