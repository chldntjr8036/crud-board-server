package my.webapplication.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;

    private String title;
    @Column(name = "USER_NAME") private String userName;
    private String password, contents;
    @Column(name = "DATETIME") private LocalDateTime dateTime;
    @Column(name = "VIEW_COUNT") private int viewCount;

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public Article setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Article setPassword(String password) {
        this.password = password;
        return this;
    }

    public Article setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public Article setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Article setViewCount(int viewCount) {
        this.viewCount = viewCount;
        return this;
    }
}
