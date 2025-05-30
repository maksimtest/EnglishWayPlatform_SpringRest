package platform.dtos;

import lombok.Data;
import platform.entities.Course;
import platform.entities.Lesson;
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
    private List<LessonDto> lessons = new ArrayList<>();

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

    public static CourseDto getInstanceWithLessons(Course course, int lessonLimit) {
        CourseDto dto = getInstance(course);
        for (int i = 0; i < Math.min(lessonLimit, course.getLessons().size()); i++) {
            Lesson lesson = course.getLessons().get(i);
            dto.getLessons().add(LessonDto.getInstance(lesson));
        }
        dto.getLessons().sort(Comparator.comparingInt(LessonDto::getNum));
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
        if (lessons == null || lessons.isEmpty()) {
            if (course.getLessons() != null && !course.getLessons().isEmpty()) {
                course.getLessons().clear();
            }
        } else {
            // Remove old sentence without referenced id
            course.getLessons().removeIf(lesson ->
                    lessons.stream().noneMatch(s -> s.getId() != null
                                                    && s.getId().equals(lesson.getId()))
            );
            System.out.println("before for by sentences, course.getLessons()=" + course.getLessons());
            for (LessonDto dto : lessons) {
                boolean updated = false;
                for (Lesson lesson : course.getLessons()) {
                    if (Objects.equals(lesson.getId(), dto.getId())) {
                        updated = true;
                        dto.updateLesson(lesson);
                        break;
                    }
                }
                if (!updated) {
                    Lesson newLesson = new Lesson();
                    newLesson.setCourse(course);
                    dto.updateLesson(newLesson);
                    course.getLessons().add(newLesson);
                }
            }
        }
    }
}
