package platform.dtos;


import lombok.Data;

import java.util.List;

@Data
public class EditCourseDto {
    private CourseDto course;
    private List<UnitDto> lessons;
}
