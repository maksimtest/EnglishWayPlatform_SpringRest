package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="levels")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

//    public final static String STARTER = "Starter";
//    public final static String BEGINNER = "Beginner";
//    public final static String PRE_INTERMEDIATE = "Pre-intermediate";
//    public final static String INTERMEDIATE="Intermediate";
//    public final static String UPPER_INTERMEDIATE="Upper-intermediate";
//    public final static String ADVANCED="Advanced";
}
