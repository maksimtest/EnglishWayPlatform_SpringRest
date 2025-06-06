package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.*;
import platform.services.StudentService;

@RestController
@RequiredArgsConstructor
public class AdminStudentsController {
    private final StudentService studentService;

    @PostMapping("admin/get-students-activity") // by manager
    public ActivitiesListDto getStudentsActivity() {
        System.out.println("AdminStudentsController(get-students-activity)");
        ActivitiesListDto studentsDto = studentService.getStudentsActivity();
        System.out.println("AdminStudentsController(get-students-activity): return studentsDto=" + studentsDto);
        return studentsDto;
    }

    @PostMapping("admin/create-student-activity")
    public StudentDto createStudentActivity(@RequestBody StudentActivityDto studentActivityDto) {
        System.out.println("AdminStudentsController(create-student-activity): studentActivityDto=" + studentActivityDto);
        StudentDto studentDto = studentService.createStudentActivity(studentActivityDto);
        System.out.println("AdminStudentsController(create-student-activity): return studentDto=" + studentDto);
        return studentDto;
    }
}
