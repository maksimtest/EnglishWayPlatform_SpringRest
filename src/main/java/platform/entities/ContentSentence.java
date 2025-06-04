package platform.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name="content_sentences")
public class ContentSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String text;
    @Column
    private String answer;
    @Column
    private String case1;
    @Column
    private String case2;
    @Column
    private String case3;

    @Column(name="correct_case")
    private int correctCase;

    @Column
    private int num;

    @Column(name="sort")
    private int order;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = true)
    private Content content;
}
