package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.entities.LevelTestResult;
import platform.repositories.LevelTestResultRepository;

@Service
@RequiredArgsConstructor
public class LevelTestResultService {
    private final LevelTestResultRepository levelTestResultRepository;

    public LevelTestResult saveResult(LevelTestResult result) {
        System.out.println("LevelTestService.saveResult, result=" + result);
        return levelTestResultRepository.save(result);
    }

}
