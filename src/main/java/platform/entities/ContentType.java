package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="content_types")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
