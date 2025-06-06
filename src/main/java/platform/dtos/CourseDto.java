package platform.dtos;

import lombok.Data;
import platform.entities.Course;
import platform.entities.Unit;
import platform.entities.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String img;
    private String level;
    private String codeLevel;
    private String complication;
    private List<UnitDto> units = new ArrayList<>();

    public static CourseDto getInstance(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setImg(course.getImg());
        dto.setLevel(course.getLevel().getName() + "(" + course.getLevel().getCode() + ")");
        dto.setComplication(course.getComplication());
        dto.setCodeLevel(course.getLevel().getCode());
        return dto;
    }

    public static CourseDto getInstanceWithUnits(Course course, int unitLimit) {
        CourseDto dto = getInstance(course);
        for (int i = 0; i < Math.min(unitLimit, course.getUnits().size()); i++) {
            Unit unit = course.getUnits().get(i);
            dto.getUnits().add(UnitDto.getInstance(unit));
        }
        dto.getUnits().sort(Comparator.comparingInt(UnitDto::getNum));
        return dto;
    }

    public void updateCourse(Course course, Level level) {
        course.setName(name);
        course.setImg(img);
        course.setComplication(complication);
        if (level != null) {
            course.setLevel(level);
        }
    }

    public void updateCourseWithLessons(Course course, Level level) {
        //course.setId(id);
        course.setName(name);
        course.setImg(img);
        course.setComplication(complication);
        if (level != null) {
            course.setLevel(level);
        }
        if (units == null || units.isEmpty()) {
            if (course.getUnits() != null && !course.getUnits().isEmpty()) {
                course.getUnits().clear();
            }
        } else {
            // Remove old sentence without referenced id
            course.getUnits().removeIf(unit ->
                    units.stream().noneMatch(s -> s.getId() != null
                                                    && s.getId().equals(unit.getId()))
            );
            System.out.println("before for by sentences, course.getLessons()=" + course.getUnits());
            for (UnitDto dto : units) {
                boolean updated = false;
                for (Unit unit : course.getUnits()) {
                    if (Objects.equals(unit.getId(), dto.getId())) {
                        updated = true;
                        dto.updateUnit(unit);
                        break;
                    }
                }
                if (!updated) {
                    Unit newUnit = new Unit();
                    newUnit.setCourse(course);
                    dto.updateUnit(newUnit);
                    course.getUnits().add(newUnit);
                }
            }
        }
    }
}
