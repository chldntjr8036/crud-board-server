package my.webapplication.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.webapplication.board.domain.Article;
import my.webapplication.board.repository.BoardRepository;
import my.webapplication.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private JacksonTester<Article> json;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }


    @Test
    public void getArticleTest() throws Exception {
        long id = 1;
        LocalDateTime timestamp = LocalDateTime.of(2019, 11,
                14,13, 20);
        Article storedArticle = new Article(1,"title","choi",
                "choi", "hello",timestamp,0);
        when(boardService.getArticle(id)).thenReturn(storedArticle);

        String expectedContents =
                "{\"id\":1,\"title\":\"title\",\"userName\":\"choi\",\"password\":\"choi\"," +
                        "\"contents\":\"hello\",\"dateTime\":\"2019-11-14T13:20:00\"," +
                        "\"viewCount\":0}";

        mockMvc.perform(
                get("/board/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContents));
    }

}
