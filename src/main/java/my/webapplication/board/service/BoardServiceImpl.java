package my.webapplication.board.service;

import my.webapplication.board.domain.Article;
import my.webapplication.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public Article getArticle(long id) {
        Article article = boardRepository.findById(id);
        article.setViewCount(article.getViewCount()+1);
        return insertArticle(article);
    }

    @Override
    public Article insertArticle(Article article) {
        return boardRepository.save(article);
    }

    @Override
    @Transactional
    public void deleteArticle(long id) {
        boardRepository.deleteArticleById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return (List<Article>)boardRepository.findAll();
    }
}
