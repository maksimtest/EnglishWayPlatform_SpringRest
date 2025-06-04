package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.CourseDto;
import platform.services.CourseService;


@RestController
@RequiredArgsConstructor
public class AdminLessonController {
    private final CourseService courseService;
    //private final LessonService lessonService;

    @PostMapping("admin/get-units")
    public CourseDto getEditLessonsDto(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(get-lessons): CourseDto="+courseDto);
        CourseDto dto = courseService.getCourseByIdAndUsername(courseDto.getId(), null);
        System.out.println("AdminLessonController(get-lessons): return dto="+dto);
        return dto;
    }

    @PostMapping("admin/update-units")
    public CourseDto updateLesson(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(update-lesson) post courseDto="+courseDto);
        CourseDto resultCourseDto = courseService.updateCourse(courseDto);
        System.out.println("AdminLessonController(update-lesson) return resultCourseDto="+resultCourseDto);
        return courseDto;
    }
}
