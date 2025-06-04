package platform.dtos;

import lombok.Data;

@Data
public class ResultDto {
    Long contentId;
    String answer;
    int score;
}
