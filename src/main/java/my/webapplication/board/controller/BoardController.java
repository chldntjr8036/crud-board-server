package my.webapplication.board.controller;

import my.webapplication.board.domain.Article;
import my.webapplication.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/articles")
    public List<Article> getArticles(){
        return boardService.getAllArticles();
    }

    //read
    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable("id") long id) {
        return boardService.getArticle(id);
    }

    //create
    @PostMapping("/article")
    public Article createArticle(@RequestBody Article article) {
        article.setDateTime(LocalDateTime.now());
        return boardService.insertArticle(article);
    }

    //update
    @PutMapping("/articles/{id}")
    public Article updateArticle(@PathVariable long id, @RequestBody Article newArticle) {
        Article storedArticle = boardService.getArticle(id);
        storedArticle.setUserName(newArticle.getUserName());
        storedArticle.setTitle(newArticle.getTitle());
        storedArticle.setContents(newArticle.getContents());
        storedArticle.setPassword(newArticle.getPassword());
        storedArticle.setDateTime(LocalDateTime.now());

        boardService.insertArticle(storedArticle);
        return storedArticle;
    }

    //delete
    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable long id) {
        boardService.deleteArticle(id);
    }

}
