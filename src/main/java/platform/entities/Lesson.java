package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_time")
    private LocalDateTime startTime;

    @Column(name="duration_minutes", length = 4)
    private int durationMinutes;

    @Column
    private String topic;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private StudentActivity activity;


    @Enumerated(EnumType.STRING)
    private LessonStatus status;
}
