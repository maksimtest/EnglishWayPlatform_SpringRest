package platform.dtos;

import lombok.Data;
import platform.entities.*;

import java.util.*;

@Data
public class ContentDto {
    private Long id;
    private String task;
    private String text;
    private String url;
    private String code;
    private Long menuItemId;
    private int order;
    private int numValue;
    private String type;
    private List<ContentSentenceDto> sentences;
    private String userAnswer = "";
    private int score;

    public static ContentDto getInstance(Content content) {
        ContentDto contentDto = new ContentDto();
        contentDto.setId(content.getId());
        contentDto.setTask(content.getTask());
        contentDto.setText(content.getText());
        contentDto.setUrl(content.getUrl());
        contentDto.setCode(content.getCode());
        contentDto.setNumValue(content.getNumValue());
        contentDto.setMenuItemId(content.getMenuItem().getId());
        contentDto.setOrder(content.getOrder());
        contentDto.setType(content.getType().getName());
        contentDto.setSentences(new ArrayList<>());
        List<ContentSentence> sentences = content.getSentences();
        if (sentences != null) {
            List<ContentSentenceDto> sentencesDto;

            sentencesDto = sentences
                    .stream()
                    .map(ContentSentenceDto::getInstance)
                    .sorted((a, b) -> {
                        return a.getOrder() - b.getOrder();
                    })
                    .toList();
            contentDto.setSentences(sentencesDto);
        }

        return contentDto;
    }

    public void updateContent(Content content) {
        content.setTask(getTask());
        content.setText(getText());
        content.setUrl(getUrl());
        content.setCode(getCode());
        content.setNumValue(getNumValue());
        content.setOrder(getOrder());
        // update sentences
        if (sentences == null || sentences.isEmpty()) {
            if (content.getSentences() != null && !content.getSentences().isEmpty()) {
                // TODO: maybe need call remove(index) method for repository visible
                content.getSentences().clear();
            }
        } else {
            // Remove old sentence without referenced id
            content.getSentences().removeIf(sent1 ->
                    sentences.stream().noneMatch(s -> s.getId() != null
                                                      && s.getId().equals(sent1.getId()))
            );
            System.out.println("before for by sentences, content.getSentences()=" + content.getSentences());
            for (ContentSentenceDto dto : sentences) {
                boolean updated = false;
                for (ContentSentence sent : content.getSentences()) {
                    if (Objects.equals(sent.getId(), dto.getId())) {
                        updated = true;
                        dto.updateContentSentence(sent);
                        break;
                    }
                }
                if (!updated) {
                    ContentSentence newSentence = new ContentSentence();
                    newSentence.setContent(content);
                    dto.updateContentSentence(newSentence);
                    content.getSentences().add(newSentence);
                }
            }
        }
    }
}
