package platform.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String task;

    @Column(length = 4000)
    private String text;

    @Column
    private String url;

    @Column
    private String code;

    @Column(name="sort")
    private int order;

    @Column(name="num_value")
    private int numValue;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    private ContentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = true)
    private MenuItem menuItem;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentSentence> sentences;
}
