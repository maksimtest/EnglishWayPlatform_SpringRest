package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.CourseDto;
import platform.services.CourseService;


@RestController
@RequiredArgsConstructor
public class AdminUnitController {
    private final CourseService courseService;
    //private final LessonService lessonService;

    @PostMapping("admin/get-units")
    public CourseDto getUnits(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(get-units): CourseDto="+courseDto);
        CourseDto dto = courseService.getCourseByIdAndUsername(courseDto.getId(), null);
        System.out.println("AdminLessonController(get-units): return dto="+dto);
        return dto;
    }

    @PostMapping("admin/update-units")
    public CourseDto updateUnits(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(update-units) post courseDto="+courseDto);
        CourseDto resultCourseDto = courseService.updateCourse(courseDto);
        System.out.println("AdminLessonController(update-units) return resultCourseDto="+resultCourseDto);
        return courseDto;
    }
}
