package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import platform.dtos.ContentDto;
import platform.dtos.UnitContentsDto;
import platform.dtos.UnitMenuDto;
import platform.dtos.MenuItemDto;
import platform.entities.*;
import platform.repositories.ContentRepository;
import platform.repositories.ContentTypeRepository;
import platform.repositories.UnitRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final UnitRepository lessonRepository;
    private final MenuItemService menuItemService;
    private final ContentTypeRepository contentTypeRepository;
    private final ResultService resultService;

    public List<Content> getContents(Long menuItemId) {
        if (menuItemId == null) {
            return new ArrayList<>();
        }
        return contentRepository.findAllContentsByMenuAndOrdered(menuItemId);
    }

    public UnitContentsDto getLessonContents(UnitMenuDto unitMenuDto,
                                             String username,
                                             boolean isAdmin) {

        Long lessonId = unitMenuDto.getUnitId();
        Unit unit = lessonRepository.findById(lessonId).orElseThrow();
        if (username != null && username.isEmpty()) {
            // TODO limited for username
        }

        UnitContentsDto lessonContentsDto = new UnitContentsDto();
        lessonContentsDto.setUnitName(unit.getName());

        List<MenuItemDto> menuItems = menuItemService.getMenuItemsByLesson(lessonId);
        lessonContentsDto.setMenuItems(menuItems);

        Long activeMenuItemId = unitMenuDto.getMenuItemId();
        lessonContentsDto.setActiveMenuId(activeMenuItemId);
        if (menuItems == null || menuItems.isEmpty()) {
            return lessonContentsDto;
        }

        if (activeMenuItemId == null || menuItemService.findById(activeMenuItemId).isEmpty()) {
            activeMenuItemId = menuItems.get(0).getId();
        }
        lessonContentsDto.setActiveMenuId(activeMenuItemId);

        List<Content> contents = getContents(activeMenuItemId);

        List<ContentDto> contentsDto = contents.stream().map(ContentDto::getInstance).toList();
        if (!isAdmin) {
            resultService.updateContentDto(contentsDto);
        }
        lessonContentsDto.setContents(contentsDto);
        return lessonContentsDto;
    }

    public ContentDto createContent(ContentDto contentDto) {
        Long menuItemId = contentDto.getMenuItemId();
        MenuItem menuItem = menuItemService.findById(menuItemId).orElseThrow();
        ContentType contentType = contentTypeRepository.findByName(contentDto.getType()).orElseThrow();
        int maxOrder = contentRepository.findAllContentsByMenuAndOrdered(menuItemId)
                .stream()
                .mapToInt(Content::getOrder)
                .max()
                .orElse(0);

        Content content = new Content();
        if (contentDto.getTask() == null || contentDto.getTask().isEmpty()) {
            content.setTask("Task " + contentType.getName());
        } else {
            content.setTask(contentDto.getTask());
        }
        content.setText(contentDto.getText());
        content.setMenuItem(menuItem);
        content.setType(contentType);
        content.setOrder(maxOrder + 2);
        System.out.println("before save new content:" + content);
        content = contentRepository.save(content);

        return ContentDto.getInstance(content);
    }

    public ContentDto updateContent(ContentDto contentDto) {
        Content content = contentRepository.findById(contentDto.getId()).orElseThrow();
        System.out.println("updateContent, before update, content:" + content);
        contentDto.updateContent(content);
        System.out.println("updateContent, after update, content:" + content);

        content = contentRepository.save(content);

        return ContentDto.getInstance(content);
    }

    public ResponseEntity<?> deleteContent(ContentDto contentDto) {
        contentRepository.deleteById(contentDto.getId());
        return ResponseEntity.ok().build();
    }

    public Content getContentById(Long contentId) {
        return contentRepository.findById(contentId).orElseThrow();
    }
}
