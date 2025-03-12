package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
//@Entity
public class MenuItem {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String name;

    @Column
    String url;

    @Column
    String img;

    @Column
    String title;

    public MenuItem(String name, String url, String img, String title) {
        this.name = name;
        this.url = url;
        this.img = img;
        this.title = title;
    }
//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
}
