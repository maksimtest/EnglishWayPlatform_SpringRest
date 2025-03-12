package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//@Entity
@Data
//@Table(name = "menu")
public class Menu {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column
    private String type;

//    @ManyToOne
//    @JoinColumn(name = "role_id", nullable = true)
    private Role role;

//    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> items;
}
