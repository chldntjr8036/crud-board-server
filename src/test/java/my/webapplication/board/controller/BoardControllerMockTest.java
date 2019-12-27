package my.webapplication.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.webapplication.board.domain.Article;
import my.webapplication.board.repository.BoardRepository;
import my.webapplication.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static my.webapplication.board.controller.BoardControllerTestUtils.*;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
public class BoardControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @MockBean
    BoardRepository boardRepository;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
    }

    /*
    GET /article 단위테스트
     */
    @Test
    public void getArticleTest() throws Exception {
        long id = 1;
        LocalDateTime timestamp = LocalDateTime.of(2019, 11,
                14,13, 20);
//        Article storedArticle = new Article(1,"title","choi",
//                "choi", "hello",timestamp,0);
        Article storedArticle = makeOneArticle(1);
        when(boardService.getArticle(id)).thenReturn(storedArticle);

        String expectedContents =
                "{\"id\":1,\"title\":\"title1\"," +
                        "\"userName\":\"userName1\",\"password\":\"password1\"," +
                        "\"contents\":\"content1\",\"dateTime\":\"1989-12-11T00:00:01\"," +
                        "\"viewCount\":0}";

        mockMvc.perform(
                get("/articles/1")
        )
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContents));
    }

    /*
    GET /articles 단위테스트
     */
    @Test
    public void testGetArticles() throws Exception {

        List<Article> articles = makeArticles(3);

        when(boardService.getAllArticles()).thenReturn(articles);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().json(articleListToJson(articles)));
    }

    /*
    POST /article 단위테스트
     */
    @Test
    public void testCreateArticle() throws Exception {

        Article article = makeOneArticle(1);

        when(boardService.insertArticle(article)).thenReturn(article);

        mockMvc.perform(post("/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleToJson(article))
        ).andExpect(status().isOk())
                .andExpect(content().json(articleToJson(article)));
    }

    /*
    url : PUT /articles/{id}
    Article updateArticle(@PathVariable long id, @RequestBody Article newArticle)
    메서드 단위 테스트
     */
    @Test
    public void testUpdateArticle() throws Exception {
        int id = 1;
        Article storedArticle = makeOneArticle(id);

        Article modifiedArticle = makeOneArticle(id)
                .setContents("newContents")
                .setDateTime(LocalDateTime.of(2019,12,11,0,0,30))
                .setPassword("newPassword")
                .setTitle("newTitle")
                .setUserName("newUserName")
                .setViewCount(0);

        when(boardService.getArticle(id)).thenReturn(storedArticle);
        when(boardService.insertArticle(modifiedArticle)).thenReturn(modifiedArticle);
        mockMvc.perform(
                put("/articles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(articleToJson(modifiedArticle)))
                .andExpect(status().isOk())
                .andExpect(content().json(articleToJson(modifiedArticle)));
    }

    /*
    url : DELETE /articles/{id}
    void deleteArticle(@PathVariable long id) 단위 테스트
     */
    @Test
    public void testDeleteArticle() throws Exception {
        int id = 1;

        doNothing().when(boardService).deleteArticle(id);

        mockMvc.perform(
                delete("/articles/1")
        ).andExpect(status().isOk());
    }

}
