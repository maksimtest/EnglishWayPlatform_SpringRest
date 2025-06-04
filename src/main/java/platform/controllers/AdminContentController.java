package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.*;
import platform.services.ContentService;

@RestController
@RequiredArgsConstructor
public class AdminContentController {
    private final ContentService contentService;

    @PostMapping("admin/get-contents")
    public UnitContentsDto getLessonContents(@RequestBody UnitMenuDto unitMenuDto) {
        System.out.println("AdminContentController(get-contents): unitMenuDto="+unitMenuDto);
        UnitContentsDto unitContentDto = contentService.getLessonContents(unitMenuDto, null, true);
        System.out.println("AdminContentController(get-contents): return unitContentDto="+unitContentDto);
        return unitContentDto;
    }
    @PostMapping("admin/create-content")
    public ContentDto createCourse(@RequestBody ContentDto contentDto) {
        System.out.println("AdminContentController(create-content), contentDto="+contentDto);
        ContentDto content = contentService.createContent(contentDto);
        System.out.println("AdminContentController(create-content) return content="+content);
        return content;
    }

    @PostMapping("admin/update-content")
    public ContentDto updateContent(@RequestBody ContentDto contentDto) {
        System.out.println("AdminContentController(update-content) post contentDto="+contentDto);
        ContentDto content = contentService.updateContent(contentDto);
        System.out.println("AdminContentController(update-content) return content="+content);
        return content;
    }
    @PostMapping("admin/delete-content")
    public ResponseEntity<?> deleteContent(@RequestBody ContentDto contentDto) {
        System.out.println("AdminContentController(delete-content) post contentDto="+contentDto);
        ResponseEntity<?> result = contentService.deleteContent(contentDto);
        System.out.println("AdminContentController(delete-content) return result="+result);
        return result;
    }
}
