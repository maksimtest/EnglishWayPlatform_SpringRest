package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.entities.Level;
import platform.repositories.LevelRepository;

@Service
@RequiredArgsConstructor
public class LevelService {
    private final LevelRepository levelRepository;

    public Level getLevelByStartedName(String name, boolean isFirstDefault){
        if(name==null || name.isEmpty()){
            return null;
        }
        for(Level level: levelRepository.findAllByOrderByCodeAsc()){
            String name1 = level.getName().substring(0,4);
            if(name.substring(0,4).equalsIgnoreCase(name1)){
                return level;
            }
        }
        if(isFirstDefault){
            return levelRepository.getFirst();
        }
        return null;
    }
}
