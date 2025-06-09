package platform.dtos;

import lombok.Data;
import platform.entities.StudentActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class StudentActivityDto {
    private Long id;
    private Long studentId;
    private Long teacherId;
    private Long courseId;
    private String courseName;
    private String courseLevelCode;
    private int paymentSumma;
    private String paymentDate;
    private int paymentCount;
    private int activeNumber;

    public static StudentActivityDto getInstance(StudentActivity activity) {
        StudentActivityDto dto = new StudentActivityDto();
        dto.setId(activity.getId());
        dto.setStudentId(activity.getStudent().getId());
        dto.setTeacherId(activity.getTeacher().getId());
        dto.setCourseId(activity.getCourse().getId());
        dto.setCourseName(activity.getCourse().getName());
        dto.setCourseLevelCode(activity.getCourse().getLevel().getCode());
        dto.setPaymentSumma(activity.getPaymentSumma());
        dto.setPaymentDate(getFormatDate(activity.getPaymentDate()));
        dto.setPaymentCount(activity.getPaymentCount());
        dto.setActiveNumber(activity.getActiveUnitNum());
        return dto;
    }
    public static String getFormatDate(LocalDateTime date) {
        if(date==null){
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
        return formatter.format(date);
    }
}
