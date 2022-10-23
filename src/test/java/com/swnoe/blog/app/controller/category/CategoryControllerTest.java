package com.swnoe.blog.app.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = CategoryController.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("등록테스트(부모)")
    void registCategory() throws Exception {

        //given
        CategoryRegistForm parentCategory = CategoryRegistForm.builder()
                .name("부모 테스트")
                .depth(1)
                .build();

        //expect
        mockMvc.perform(post("/category/regist")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parentCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("부모 테스트"))
                .andExpect(jsonPath("$.depth").value(1))
                .andDo(print());

    }
    @Test
    @DisplayName("등록테스트(자식)")
    void registCategoryChild() throws Exception {

        //given
        CategoryRegistForm parentCategory = CategoryRegistForm.builder()
                .name("자식 테스트")
                .depth(2)
                .parentId(2L)
                .build();


        //expected
        mockMvc.perform(post("/category/regist")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parentCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("자식 테스트"))
                .andExpect(jsonPath("$.depth").value(2))
                .andExpect(jsonPath("$.parentId").value(2))
                .andDo(print());
    }
}