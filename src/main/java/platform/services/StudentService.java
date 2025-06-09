package platform.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.dtos.*;
import platform.entities.*;
import platform.repositories.StudentActivityRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserService userService;
    private final RoleService roleService;
    private final CourseService courseService;
    private final StudentActivityRepository studentActivityRepository;
    private final ResultService resultService;

    public ActivitiesListDto getStudentsActivity() {
        ActivitiesListDto studentsDto = new ActivitiesListDto();

        userService.listTeachers()
                .stream().map(UserDto::getInstance)
                .forEach(item->studentsDto.getTeachersList().add(item));

        for (User student : userService.listStudents()) {
            List<StudentActivity> studentActivities = studentActivityRepository.findAllByStudent(student);
            studentsDto.getStudentsList().add(StudentDto.getInstance(student, studentActivities));
        }
        for(Course course: courseService.getCourses()){
            studentsDto.getCoursesList().add(CourseDto.getInstance(course));
        }
        return studentsDto;
    }
    public List<StudentActivityDto> getStudentsActivitiesForTeacher(){
        User teacher = userService.getCurrentUser();
        List<StudentActivity> studentActivityList = studentActivityRepository.findAllByTeacher(teacher);
        // TODO: need order by date, checking actual activity etc.
        return studentActivityList.stream().map(StudentActivityDto::getInstance).toList();
    }
    @Transactional
    public List<StudentUnitDto> getStudentsForTeacher(Long studentActivityId) {
        User currentTeacher = userService.getCurrentUser();

        StudentActivity studentActivity = studentActivityRepository.findById(studentActivityId).orElseThrow();
        if(!Objects.equals(currentTeacher.getId(),studentActivity.getTeacher().getId())){
            // TODO here it is a necessary to implement a check
            return null;
        }
        Course course = studentActivity.getCourse();
        List<Unit> units = course.getUnits();

        List<StudentUnitDto> studentUnitDtoList = new ArrayList<>();
        int currentUnitNum = 0;
        for(Unit unit: units){
            StudentUnitDto studentUnitDto = new StudentUnitDto();
            studentUnitDto.setNum(unit.getNum());
            studentUnitDto.setUnitName(unit.getName());
            studentUnitDtoList.add(studentUnitDto);

            if(++currentUnitNum < studentActivity.getActiveUnitNum()){
                studentUnitDto.setNew(true);
                studentUnitDto.setPaymentAgree(true);
                break;
            }
            //  TODO order by content_num
            List<Result> results = resultService.getResultsByStudentAndUnit(studentActivity.getStudent(), unit);

            for(Result result: results){
                if(result.isHomework()){
                    studentUnitDto.getHwScores().add(result.getScore());
                } else {
                    studentUnitDto.getClassScores().add(result.getScore());
                }
            }
        }
        return studentUnitDtoList;
    }

    public StudentDto createStudentActivity(StudentActivityDto studentActivityDto){
        Long courseId = studentActivityDto.getCourseId();
        Long studentId = studentActivityDto.getStudentId();
        Long teacherId = studentActivityDto.getTeacherId();

        StudentDto studentDto = new StudentDto();
        Course course = courseService.getCourseById(courseId);
        User student = userService.findById(studentId);
        User teacher = userService.findById(studentId);

        StudentActivity studentActivity = new StudentActivity();
        studentActivity.setStudent( student);
        studentActivity.setTeacher(teacher);
        studentActivity.setCourse(course);
        studentActivity.setPaymentSumma(0);
        studentActivity.setPaymentDate(LocalDateTime.now());
        studentActivity.setPaymentCount(1000);
        studentActivity.setActiveUnitNum(1);
        studentActivityRepository.save(studentActivity);

        List<StudentActivity> studentActivities = studentActivityRepository.findAllByStudent(student);
        studentDto.setStudentActivities(
                studentActivities
                        .stream()
                        .map(StudentActivityDto::getInstance)
                        .toList());
        studentDto.setActualLevelCode(course.getLevel().getCode());
        studentDto.setUser(UserDto.getInstance( student));
        return studentDto;
    }
}
