package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.CourseDto;
import platform.services.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCourseController {
    private final CourseService courseService;

    @PostMapping("admin/get-courses")
    public List<CourseDto> getCourses() {
        System.out.println("AdminCourseController(get-courses): empty");
        List<CourseDto> list = courseService.getCoursesDto();
        System.out.println("AdminCourseController(get-courses): list="+list);
        return list;
    }
    @PostMapping("admin/create-course")
    public List<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        System.out.println("AdminCourseController(create-course), courseDto="+courseDto);
        List<CourseDto> list = courseService.createCourse(courseDto);
        System.out.println("AdminCourseController(create-course) return list="+list);
        return list;
    }

    @PostMapping("admin/update-course")
    public CourseDto saveCourse(@RequestBody CourseDto courseDto) {
        System.out.println("AdminCourseController(update-course) post CourseDto="+courseDto);
        CourseDto course = courseService.updateCourse(courseDto);
        System.out.println("AdminCourseController(update-course) return course="+course);
        return course;
    }
    @PostMapping("admin/delete-course")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseDto courseDto) {
        System.out.println("AdminCourseController(delete-course) post courseDto="+courseDto);
        ResponseEntity<?> result = courseService.deleteCourse(courseDto.getId());
        System.out.println("AdminCourseController(delete-course) return result="+result);
        return result;
    }
}
