package my.webapplication.board.service;

import my.webapplication.board.domain.Article;

import java.util.List;

public interface BoardService {
    Article getArticle(long id);
    Article insertArticle(Article article);
    void deleteArticle(long id);

    List<Article> getAllArticles();
}
