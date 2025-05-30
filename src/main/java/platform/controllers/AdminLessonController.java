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

    @PostMapping("admin/get-lessons")
    public CourseDto getEditLessonsDto(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(get-lessons): CourseDto="+courseDto);
        CourseDto dto = courseService.getCourseByIdAndUsername(courseDto.getId(), null);
        System.out.println("AdminLessonController(get-lessons): return dto="+dto);
        return dto;
    }

//    @PostMapping("admin/create-lesson-by-course")
//    public List<LessonDto> createLessonByCourse(@RequestBody LessonDto courseId) {
//        System.out.println("AdminLessonController(create-lesson-by-course), courseDto="+courseId);
//        List<LessonDto> list = lessonService.createLessonByCourse(courseId.getId());
//        System.out.println("AdminLessonController(create-lesson-by-course) return list="+list);
//        return list;
//    }
//    @PostMapping("admin/create-lesson-by-lesson")
//    public List<LessonDto> createLesson(@RequestBody LessonDto lessonDto) {
//        System.out.println("AdminLessonController(create-lesson-by-lesson), lessonDto="+lessonDto);
//        List<LessonDto> list = lessonService.createLessonBasedOnLesson(lessonDto);
//        System.out.println("AdminLessonController(create-lesson-by-lesson) return list="+list);
//        return list;
//    }
//    @PostMapping("admin/change-all-numbers-of-lesson")
//    public List<LessonDto> changeAllNumbers(@RequestBody LessonDto dto) {
//        System.out.println("AdminLessonController(change-all-numbers-of-lesson), dto="+dto);
//        List<LessonDto> list = lessonService.changeAllNumbers(dto.getId());
//        System.out.println("AdminLessonController(change-all-numbers-of-lesson) return list="+list);
//        return list;
//    }
    @PostMapping("admin/update-lessons")
    public CourseDto updateLesson(@RequestBody CourseDto courseDto) {
        System.out.println("AdminLessonController(update-lesson) post courseDto="+courseDto);
        CourseDto resultCourseDto = courseService.updateCourse(courseDto);
        System.out.println("AdminLessonController(update-lesson) return resultCourseDto="+resultCourseDto);
        return courseDto;
    }

//    @PostMapping("admin/delete-lesson")
//    public ResponseEntity<?> deleteCourse(@RequestBody CourseDto courseDto) {
//        System.out.println("AdminLessonController(delete-lesson) post courseDto="+courseDto);
//        ResponseEntity<?> result = courseService.deleteCourse(courseDto.getId());
//        System.out.println("AdminLessonController(delete-lesson) return result="+result);
//        return result;
//    }
}
