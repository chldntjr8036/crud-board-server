package my.webapplication.board.repository;

import my.webapplication.board.domain.Article;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Article, Long> {
    Article findById(long id);
    void deleteArticleById(long id);
}
