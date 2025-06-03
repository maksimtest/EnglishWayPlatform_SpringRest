package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private String description;

    @Column
    private boolean value;
}
