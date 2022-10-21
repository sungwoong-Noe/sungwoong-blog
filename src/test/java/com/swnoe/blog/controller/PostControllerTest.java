package com.swnoe.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.app.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @DisplayName("게시글 저장 컨트롤러")
    void 게시글_저장() throws Exception {

        //given
        PostRequest request = PostRequest.builder()
                .title("제목1")
                .content("게시글1")
                .build();
        String jsonData = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 저장 - 예외")
    void 게시글_저장_예외() throws Exception {
        //given
        PostRequest request = PostRequest.builder()
                .title("제목1")
                .build();
        String jsonData = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

}
