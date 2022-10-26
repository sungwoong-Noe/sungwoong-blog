package com.swnoe.blog.app.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swnoe.blog.app.service.category.CategoryService;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.category.ParentCategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("등록테스트(부모)")
    void registCategory() throws Exception {

        //given
        given(categoryService.regist(any())).willReturn(CategoryResponse.builder().name("부모 카테고리").depth(1).build());
        CategoryRegistForm parentCategory = CategoryRegistForm.builder()
                .name("부모 테스트")
                .depth(1)
                .build();

        //expect
        mockMvc.perform(post("/category/regist")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parentCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("부모 카테고리"))
                .andExpect(jsonPath("$.depth").value(1))
                .andDo(print());

    }
    @Test
    @DisplayName("등록테스트(자식)")
    void registCategoryChild() throws Exception {

        //given
        given(categoryService.regist(any())).willReturn(CategoryResponse.builder().name("자식 카테고리").depth(2).parentId(2L).build());
        CategoryRegistForm parentCategory = CategoryRegistForm.builder()
                .name("자식 카테고리")
                .depth(2)
                .parentId(2L)
                .build();


        //expected
        mockMvc.perform(post("/category/regist")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parentCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("자식 카테고리"))
                .andExpect(jsonPath("$.depth").value(2))
                .andExpect(jsonPath("$.parentId").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("부모 카테고리 조회")
    void getParentCategory() throws Exception {


        List<CategoryResponse> childCategories = new ArrayList<>();

        childCategories.add(CategoryResponse.builder().id(4L).name("자식 카테고리").depth(2).parentId(2L).build());
        childCategories.add(CategoryResponse.builder().id(5L).name("자식 카테고리").depth(2).parentId(2L).build());
        childCategories.add(CategoryResponse.builder().id(10L).name("자식 테스트").depth(2).parentId(2L).build());
        childCategories.add(CategoryResponse.builder().id(12L).name("자식 테스트").depth(2).parentId(2L).build());
        childCategories.add(CategoryResponse.builder().id(13L).name("자식 테스트").depth(2).parentId(2L).build());
        childCategories.add(CategoryResponse.builder().id(15L).name("자식 테스트").depth(2).parentId(2L).build());




        ParentCategoryResponse parentCategory = ParentCategoryResponse.builder()
                .id(2L)
                .name("asdasd")
                .depth(1)
                .childCategories(childCategories)
                .build();

        //given
        given(categoryService.getParentCategory(any())).willReturn(parentCategory);


        //expected
        mockMvc.perform(get("/category/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("asdasd"))
                .andDo(print());
    }
}