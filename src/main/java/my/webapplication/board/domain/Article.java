package my.webapplication.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;

    private String title;
    @Column(name = "USER_NAME") private String userName;
    private String password, contents;
    @Column(name = "DATETIME") private LocalDateTime dateTime;
    @Column(name = "VIEW_COUNT") private int viewCount;
}
