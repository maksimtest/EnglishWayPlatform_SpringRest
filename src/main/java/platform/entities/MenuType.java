package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="menu_types")
public class MenuType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = true)
//    private Course course;
}
