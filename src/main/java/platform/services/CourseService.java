package platform.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import platform.dtos.CourseDto;
import platform.entities.*;
import platform.repositories.CourseRepository;
import platform.repositories.HistoryRepository;
import platform.repositories.StudentActivityRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final LevelService levelService;
    private final UserService userService;
    private final StudentActivityRepository studentActivityRepository;
    private final HistoryRepository historyRepository;

    public List<Course> getCourses() {
        return courseRepository.findAllByOrderByLevelIdAsc();
    }
    public Course getCourseById(Long courseId){
        return courseRepository.findById(courseId).orElseThrow();
    }
    public CourseDto getCourseByIdAndUsername(Long courseId, String username) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        int lessonLimit = 1000;
        if (username != null && !username.isEmpty()) {
            // not admin that need use limit
            User user = userService.findByUsername(username).orElseThrow();
            // TODO: limited course by user
            StudentActivity activity = studentActivityRepository.findByUserAndCourse(user, course).orElseThrow();
            lessonLimit = Math.max(activity.getActiveNumber(),activity.getPaymentCount());
        }
        History history = new History();
        history.setUser(userService.getCurrentUser());
        history.setType("Create lesson");
        history.setInfo("Edit course: "+course.getName());
        history.setDate(LocalDateTime.now());
        historyRepository.save(history);

        return CourseDto.getInstanceWithLessons(course, lessonLimit);
    }

    public List<CourseDto> getCoursesDto() {
        return getCourses().stream().map(CourseDto::getInstance).toList();
    }

    @Transactional
    public List<CourseDto> createCourse(CourseDto courseDto) {
        Course course = new Course();
        Level level = (levelService.getLevelByStartedName(courseDto.getLevel(), true));
        courseDto.updateCourse(course, level);
        System.out.println("CourseService.createCourse, course: " + course);
        courseRepository.save(course);

        History history = new History();
        history.setUser(userService.getCurrentUser());
        history.setType("Create course");
        history.setInfo("Create course: "+course.getName());
        history.setDate(LocalDateTime.now());
        historyRepository.save(history);

        return getCoursesDto();
    }

    @Transactional
    public CourseDto updateCourse(CourseDto courseDto) {
        Course course = courseRepository.findById(courseDto.getId()).orElseThrow();
        System.out.println("CourseService.updateCourse_1, course: " + course);
        courseDto.updateCourseWithLessons(course, levelService.getLevelByStartedName(courseDto.getLevel(), false));
        System.out.println("CourseService.updateCourse_2, courseDto: " + courseDto);
        System.out.println("CourseService.updateCourse_3, course: " + course);
        course = courseRepository.save(course);
        return CourseDto.getInstanceWithLessons(course, 1000);
    }

    public ResponseEntity<?> deleteCourse(Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
