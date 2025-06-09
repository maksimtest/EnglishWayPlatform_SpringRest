package platform.dtos;

import lombok.Data;
import platform.entities.StudentActivity;
import platform.entities.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDto {
    private UserDto user;
    private String actualLevelCode ="00";
    private List<StudentActivityDto> studentActivities = new ArrayList<>();

    public static StudentDto getInstance(User user, List<StudentActivity> activities) {
        StudentDto dto = new StudentDto();
        dto.setUser(UserDto.getInstance(user));
        if (activities != null && !activities.isEmpty()) {
            dto.setStudentActivities(
                    activities.stream()
                            .map(StudentActivityDto::getInstance)
                            .toList()
            );
            activities.sort((a,b)->b.getCourse().getLevel().getCode().compareToIgnoreCase(a.getCourse().getLevel().getCode()));
            StudentActivity maxActivity = activities.get(0);
            dto.setActualLevelCode(maxActivity.getCourse().getLevel().getCode());
        }
        return dto;
    }
}
