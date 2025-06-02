package platform.datas;

import lombok.Data;
import platform.entities.Level;

@Data
public class MarkLevelTest {
    private Level level;
    private int count;
    private int correct;

    public static MarkLevelTest getInstance(Level level) {
        MarkLevelTest mark = new MarkLevelTest();
        mark.setLevel(level);
        return mark;
    }

    public Long getId() {
        return level == null ? 0 : level.getId();
    }
}
