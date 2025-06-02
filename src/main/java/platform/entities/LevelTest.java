package platform.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

//@Builder
@Data
@Entity
@Table(name = "level_tests")
public class LevelTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int num;
    @Column(nullable = false)
    private String question;
    @Column
    private String case1;
    @Column
    private String case2;
    @Column
    private String case3;
    @Column
    private int correct;
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = true)
    private Level level;
}
