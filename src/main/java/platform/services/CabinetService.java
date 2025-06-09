package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dtos.CabinetDto;
import platform.dtos.CourseDto;
import platform.dtos.MenuItemDto;
import platform.entities.*;
import platform.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabinetService {
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final RoleService roleService;

    private final CourseRepository courseRepository;
    private final MenuTypeRepository menuTypeRepository;
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
                .map(str -> str.substring("ROLE_" .length()).toLowerCase())
                .collect(Collectors.joining(","));

        List<MenuItem> menuItems = menuItemService.getMenuByType(roles, menuTypeRepository.getMainMenuType());
        cabinetDto.setMainMenu(menuItems.stream().map(MenuItemDto::getInstance).toList());
        cabinetDto.setRoles(strRoles);
        return cabinetDto;
    }

    public List<CourseDto> getCoursesForStudentAndTeachers() {
        // cabinet
        // getCourses for Student
        // getCourses for Teacher
        User currentUser = userService.getCurrentUser();
        Settings showCourseIfExistActivitySettings = settingsRepository.findByCode("show_courses_if_student_activity_exists").orElseThrow();

        boolean showAllCourses = !showCourseIfExistActivitySettings.isValue() ||
                                 !roleService.isStudentRolePresent(currentUser);

        List<Course> courses;
        if (showAllCourses) {
            courses = courseRepository.findAllByOrderByLevelIdAsc();
        } else {
            courses = new ArrayList<>();
            List<StudentActivity> list = studentActivityRepository.findAllByStudent(currentUser);
            list.forEach(item -> courses.add(item.getCourse()));
        }
        return courses.stream().map(CourseDto::getInstance).toList();
    }

    public CourseDto getUnitsForUser(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow();
        User user = userService.getCurrentUser();

        int lessonLimit = 1;
        if(roleService.isStudentRolePresent(user)) {
            StudentActivity studentActivity = studentActivityRepository.findByStudentAndCourse(user, course).orElse(null);

            if (studentActivity == null) {
                Settings accessFirstLessonSettings = settingsRepository.findByCode("always_access_first_lesson").orElseThrow();
                lessonLimit = accessFirstLessonSettings.isValue() ? 1 : 0;
            } else {
                lessonLimit = studentActivity.getActiveUnitNum();
            }
        } else {
            lessonLimit = course.getUnits().size();
        }

        return CourseDto.getInstanceWithUnits(course, lessonLimit);
    }

//    public ResultDto saveResults(List<ResultDto> results, String username) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//        LocalDateTime now = LocalDateTime.now();
//        Long lessonId = null;
//        int sumResult = 0;
//        for (ResultDto result : results) {
//            //Content content = contentRepository.findById(result.getContentId()).orElseThrow();
//            ContentSentence sentence = contentSentenceRepository.findById(result.getSentenceId()).orElseThrow();
//            Content content = sentence.getContent();
//            lessonId = content.getMenuItem().getLesson().getId();
//            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
//
//            Result newResult = new Result();
//            newResult.setUser(user);
//            newResult.setContent(content);
//            newResult.setLesson(lesson);
//            newResult.setSentence(sentence);
//            newResult.setDateTime(now);
//            newResult.setNumAnswer(result.getNumAnswer());
//
//            if ("tests".equalsIgnoreCase(content.getType().getName())) {
//                int resItem = result.getNumAnswer() == sentence.getCorrectCase() ? 1 : 0;
//                sumResult += resItem;
//            } else {
//
//            }
//            resultRepository.save(newResult);
//        }
//        int r = 100 * sumResult / results.size();
//        ResultDto finalResult = new ResultDto();
//        finalResult.setNumAnswer(r);
//        return finalResult;
//    }

}
