package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dtos.CabinetDto;
import platform.dtos.CourseDto;
import platform.dtos.MenuItemDto;
import platform.dtos.ResultDto;
import platform.entities.*;
import platform.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabinetService {
    private final UserService userService;
    private final MenuItemService menuItemService;

    private final CourseRepository courseRepository;
    private final MenuTypeRepository menuTypeRepository;
    private final ResultRepository resultRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ContentSentenceRepository contentSentenceRepository;
    private final StudentActivityRepository studentActivityRepository;
    private final SettingsRepository settingsRepository;

    public CabinetDto getCabinet(String username) {
        System.out.println("CabinetService.getCabinet, username: " + username);
        User user = userService.findByUsername(username).orElse(new User());
        System.out.println("\tCabinetService.getCabinet, user: " + user.getName());
        CabinetDto cabinetDto = new CabinetDto();
        List<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            return cabinetDto;
        }

        String strRoles = roles.stream()
                .map(Role::getName)
                .map(str -> str.substring("ROLE_".length()).toLowerCase())
                .collect(Collectors.joining(","));

        List<MenuItem> menuItems = menuItemService.getMenuByType(roles, menuTypeRepository.getMainMenuType());
        cabinetDto.setMainMenu(menuItems.stream().map(MenuItemDto::getInstance).toList());
        cabinetDto.setRoles(strRoles);
        return cabinetDto;
    }

    public List<CourseDto> getCoursesForStudent(String username) {
        User user = userService.findByUsername(username).orElseThrow();
        Settings showByStudentActivitySettings = settingsRepository.findByCode("show_courses_if_student_activity_exists").orElseThrow();
        boolean alwaysShowCourse = !showByStudentActivitySettings.isValue() ||
                                   !userService.isStudent(user);
        List<CourseDto> coursesDto = new ArrayList<>();
        for (Course course : courseRepository.findAllByOrderByLevelIdAsc()) {
            boolean isEmpty = studentActivityRepository.findByUserAndCourse(user, course).isEmpty();
            if (alwaysShowCourse ||
                !studentActivityRepository.findByUserAndCourse(user, course).isEmpty()) {
                coursesDto.add(CourseDto.getInstance(course));
            }
        }
        return coursesDto;
    }

    public CourseDto getCourseLessonsForStudent(Long courseID, String username) {
        Course course = courseRepository.findById(courseID).orElseThrow();
        User user = userService.findByUsername(username).orElseThrow();

        int lessonLimit = 1;
        StudentActivity studentActivity = studentActivityRepository.findByUserAndCourse(user, course).orElse(null);
        if (studentActivity == null) {
            Settings accessFirstLessonSettings = settingsRepository.findByCode("always_access_first_lesson").orElseThrow();
            lessonLimit = accessFirstLessonSettings.isValue() ? 1 : 0;
        } else {
            lessonLimit = studentActivity.getActiveNumber();
        }

        return CourseDto.getInstanceWithLessons(course, lessonLimit);
    }

    public ResultDto saveResults(List<ResultDto> results, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        Long lessonId = null;
        int sumResult = 0;
        for (ResultDto result : results) {
            //Content content = contentRepository.findById(result.getContentId()).orElseThrow();
            ContentSentence sentence = contentSentenceRepository.findById(result.getSentenceId()).orElseThrow();
            Content content = sentence.getContent();
            lessonId = content.getMenuItem().getLesson().getId();
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();

            Result newResult = new Result();
            newResult.setUser(user);
            newResult.setContent(content);
            newResult.setLesson(lesson);
            newResult.setSentence(sentence);
            newResult.setDateTime(now);
            newResult.setNumAnswer(result.getNumAnswer());

            if ("tests".equalsIgnoreCase(content.getType().getName())) {
                int resItem = result.getNumAnswer() == sentence.getCorrectCase() ? 1 : 0;
                sumResult += resItem;
            } else {

            }
            resultRepository.save(newResult);
        }
        int r = 100 * sumResult / results.size();
        ResultDto finalResult = new ResultDto();
        finalResult.setNumAnswer(r);
        return finalResult;
    }

}
