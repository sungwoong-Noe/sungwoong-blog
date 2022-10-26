package com.swnoe.blog.app.service.category;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.category.ParentCategoryResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;



    @Test
    void 카테고리_등록(){

        //given
        CategoryRegistForm request = new CategoryRegistForm();
        request.setName("자식 카테고리");
        request.setDepth(2);
        request.setParentId(2L);


        //when
        CategoryResponse regist = categoryService.regist(request);
        Category registCategory = categoryRepository.findById(regist.getId()).get();


        //then
        Assertions.assertThat(regist.getName()).isEqualTo(registCategory.getName());

    }


    @Test
    void 부모카테고리_하나조회(){
        //given
        Long categoryId = 2L;


        //when
        ParentCategoryResponse parentCategoryResponse = categoryService.getParentCategory(categoryId);
        List<Category> childCategories = categoryRepository.findByDepth(2);


        //then
        Assertions.assertThat(parentCategoryResponse.getChildCategories().size()).isEqualTo(childCategories.size());
    }


    @Test
    @Transactional
    void 부모카테고리_List조회(){

        //given
        Long parentCategoryId = 2L;


        //when
        ParentCategoryResponse parentCategory = categoryService.getParentCategory(parentCategoryId);
        Category category = categoryRepository.findById(parentCategoryId).get();


        //then
        Assertions.assertThat(parentCategory.getChildCategories().size()).isEqualTo(category.getChild().size());
        Assertions.assertThat(parentCategory.getName()).isEqualTo(category.getName());
    }

}