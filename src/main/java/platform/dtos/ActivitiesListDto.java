package platform.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActivitiesListDto {
    private List<StudentDto> studentsList = new ArrayList<>();
    private List<UserDto> teachersList = new ArrayList<>();
    private List<CourseDto> coursesList = new ArrayList<>();
}
