package platform.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name="sort")
    private int order;

    @Column
    private String url;

    @Column
    private String img;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = true)
    private MenuType type;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = true)
    private Role role;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="unit_id", nullable = true)
    private Unit unit;
}
