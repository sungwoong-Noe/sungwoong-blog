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

import java.util.List;

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
    void getParentCategoryOne(){
        //given
        Long categoryId = 2L;


        //when
        ParentCategoryResponse parentCategoryResponse = categoryService.getParentCategory(categoryId);
        List<Category> childCategories = categoryRepository.findByDepth(2);


        //then
        Assertions.assertThat(parentCategoryResponse.getChildCategories().size()).isEqualTo(childCategories.size());
    }

}