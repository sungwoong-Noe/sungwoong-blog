package com.swnoe.blog.app.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.app.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("루트 페이지 Get 요청")
    void home() throws Exception {

        //expected
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/postList"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 저장")
    void write() throws Exception {

        //expected
        mockMvc.perform(post("/write")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "제목")
                .param("content", "내용")
                .param("thumbnailUrl", "섬네일")
                .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/post/*"))
                .andDo(print());

        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 저장 - 예외")
    void write_exception() throws Exception {

        //expected
        mockMvc.perform(post("/write")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "")
                .param("content", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/writePost"))
                .andDo(print());

    }


    @Test
    @DisplayName("게시글 조회 - 카테고리별")
    void postsByCategory() throws Exception {

        //given
        Long categoryId = 5L;

        //expected
        mockMvc.perform(get("/posts/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/postsByCategory"))
                .andExpect(model().attributeExists("posts"))
                .andDo(print());
    }


}
