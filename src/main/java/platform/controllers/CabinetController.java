package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.*;
import platform.services.CabinetService;
import platform.services.ContentService;
import platform.services.ResultService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CabinetController {
    private final CabinetService cabinetService;
    private final ResultService resultService;
    private final ContentService contentService;

    @PostMapping("/inner-page")
    public CabinetDto cabinet() {
        System.out.println("CabinetController.innner-page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("CabinetController.cabinet, username: " + username);
        CabinetDto cabinetDto = cabinetService.getCabinet(username);
        System.out.println("CabinetController.innner-page, return cabinetDto=" + cabinetDto);
        return cabinetDto;
    }

    @PostMapping("/get-courses")
    public List<CourseDto> getCourses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("CabinetController(get-courses), username: " + username);
        List<CourseDto> coursesDto = cabinetService.getCoursesForStudent(username);
        System.out.println("CabinetController(get-courses), coursesDto=" + coursesDto);
        return coursesDto;
    }

    @PostMapping("/get-lessons")
    public CourseDto getLessons(@RequestBody CourseDto courseDto) {
        System.out.println("CabinetController(get-lessons), courseDto=" + courseDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("CabinetController(get-lessons), username: " + username);
        CourseDto dto = cabinetService.getCourseLessonsForStudent(courseDto.getId(), username);
        System.out.println("CabinetController(get-lessons), courseDto=" + dto);
        return dto;
    }

    @PostMapping("/get-contents")
    public LessonContentsDto getLessons(@RequestBody LessonMenuDto lessonMenuDto) {
        System.out.println("CabinetController(get-contents): lessonMenuDto=" + lessonMenuDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LessonContentsDto lessonContentDto = contentService.getLessonContents(lessonMenuDto, username, false);
        System.out.println("CabinetController(get-contents): return lessonContentDto=" + lessonContentDto);
        return lessonContentDto;
    }
    @PostMapping("/cabinet/save-results")
    public ResponseEntity<?> getLessons(@RequestBody ResultDto resultsDto) {
        System.out.println("CabinetController(cabinet/save-results): resultsDto=" + resultsDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ResponseEntity<?> result = resultService.saveResult(username, resultsDto);
        System.out.println("CabinetController(cabinet/save-results): return result=" + result);
        return result;
    }

}
