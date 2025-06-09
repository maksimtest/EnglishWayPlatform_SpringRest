package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import platform.dtos.ContentDto;
import platform.dtos.ResultDto;
import platform.entities.*;
import platform.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final UserService userService;
    private final ContentRepository contentRepository;

    public List<Result> getResultsByStudentAndUnit(User student, Unit unit){
        return resultRepository.findAllByStudentAndUnit(student, unit);
    }
    public void updateContentDto(List<ContentDto> contentsDto){
        Long userId = userService.getCurrentUser().getId();
        for(ContentDto contentDto : contentsDto){
            Result result = resultRepository.getLastResult(userId, contentDto.getId());
            if(result != null) {
                contentDto.setUserAnswer(result.getAnswer());
                contentDto.setScore(result.getScore());
            }
        }
    }
    public ResponseEntity<?> saveResult(String username, ResultDto resultsDto) {
        Long contentId = resultsDto.getContentId();
        User user = userService.findByUsername(username).orElseThrow();
        Content content = contentRepository.findById(contentId).orElseThrow();
        for (Result result : resultRepository.findLastByUserAndContentId(user.getId(), contentId)) {
            result.setLast(false);
            resultRepository.save(result);
        }
        if(resultsDto.getScore()==0 && resultsDto.getAnswer().isEmpty()){
            return ResponseEntity.ok().build();
        }
        Result newResult = new Result();
        newResult.setStudent(user);
        newResult.setContent(content);
        newResult.setUnit(content.getMenuItem().getUnit());
        newResult.setAnswer(resultsDto.getAnswer());
        newResult.setDateTime(LocalDateTime.now());
        newResult.setScore(resultsDto.getScore());
        newResult.setLast(true);
        resultRepository.save(newResult);
        return ResponseEntity.ok().build();
    }
}
