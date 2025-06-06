package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="student_activities")
public class StudentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = true)
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @Column(name="payment_summa")
    private int paymentSumma;

    @Column(name="payment_date")
    private LocalDateTime paymentDate;

    @Column(name="payment_unit_count")
    private int paymentCount;

    @Column(name="active_unit_num")
    private int activeUnitNum;
}
