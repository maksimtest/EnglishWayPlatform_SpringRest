package platform.dtos;

import lombok.Data;
import platform.entities.ContentSentence;

@Data
public class ContentSentenceDto {
    private Long id;
    private Long contentId;
    private String text;
    private String answer;
    private String case1;
    private String case2;
    private String case3;
    private int correctCase;
    private int num;
    private int order;

    public static ContentSentenceDto getInstance(ContentSentence sentence) {
        ContentSentenceDto dto = new ContentSentenceDto();
        dto.setId(sentence.getId());
        dto.setContentId(sentence.getContent().getId());
        dto.setText(sentence.getText());
        dto.setNum(sentence.getNum());
        dto.setOrder(sentence.getOrder());
        dto.setCase1(sentence.getCase1());
        dto.setCase2(sentence.getCase2());
        dto.setCase3(sentence.getCase3());
        dto.setAnswer(sentence.getAnswer());
        dto.setCorrectCase(sentence.getCorrectCase());
        return dto;
    }
    public void updateContentSentence(ContentSentence sentence){
        sentence.setAnswer(getAnswer());
        sentence.setText(getText());
        sentence.setOrder(getOrder());
        sentence.setCase1(getCase1());
        sentence.setCase2(getCase2());
        sentence.setCase3(getCase3());
        sentence.setCorrectCase(getCorrectCase());
        sentence.setNum(getNum());
    }
}
