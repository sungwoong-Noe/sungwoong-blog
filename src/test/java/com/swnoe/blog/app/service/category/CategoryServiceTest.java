package com.swnoe.blog.app.service.category;


import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.category.ParentCategoryResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;


    @Test
    @DisplayName("등록 - 부모")
    void regist_parent(){
        //given
        Category parent = Category.builder()
                .depth(1)
                .categoryName("부모").build();

        CategoryRegistForm request = CategoryRegistForm.builder()
                .name("부모")
                .build();

        when(categoryRepository.save(any())).thenReturn(parent);

        //when
        CategoryResponse registCategory = categoryService.regist(request);

        //then
        Assertions.assertThat(parent.getName()).isEqualTo(registCategory.getName());
        Assertions.assertThat(parent.getDepth()).isEqualTo(registCategory.getDepth());
        Assertions.assertThat(request.getName()).isEqualTo(registCategory.getName());
        Assertions.assertThat(registCategory.getDepth()).isEqualTo(1);
        Assertions.assertThat(registCategory.getName()).isEqualTo("부모");
    }


    @Test
    @DisplayName("등록 - 자식")
    void regist_child(){

        //given
        Category parent = Category.builder()
                .depth(1)
                .categoryName("부모").build();

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(parent));


        Category child = Category.builder()
                .depth(2)
                .parent(parent)
                .categoryName("자식").build();

        CategoryRegistForm request = CategoryRegistForm.builder()
                .name("자식")
                .depth(2)
                .parentId(1L)
                .build();

        when(categoryRepository.save(any())).thenReturn(child);


        //when
        CategoryResponse response = categoryService.regist(request);

        //then
        Assertions.assertThat(child.getName()).isEqualTo(response.getName());
        Assertions.assertThat(child.getDepth()).isEqualTo(response.getDepth());
        Assertions.assertThat(response.getDepth()).isEqualTo(2);
    }


    @Test
    @DisplayName("부모 카테고리 리스트")
    void parentCateogry_list(){

        //given
        List<Category> parentCategories = new ArrayList<>();
        for(int i = 1; i < 6; i++){
            Category parent = Category.builder()
                    .id(Long.valueOf(i))
                    .depth(1)
                    .categoryName("부모" + i)
                    .build();
            parentCategories.add(parent);
        }

        parentCategories.stream().forEach(p -> System.out.println("p = " + p.getDepth()));
        when(categoryRepository.findCategoryByParentIsNullAndDepth(1)).thenReturn(parentCategories);

        //when
        List<CategoryResponse> responseList = categoryService.parentCategoryList();

        //then
        Assertions.assertThat(parentCategories.size()).isEqualTo(responseList.size());
        Assertions.assertThat(responseList.size()).isEqualTo(5);
        Assertions.assertThat(responseList.get(0).getName()).isEqualTo("부모1");
    }


    @Test
    @DisplayName("부모 카테고리 조회")
    void getParent(){
        //given
        Category parent = Category.builder()
                .id(1L)
                .categoryName("부모1")
                .depth(1)
                .build();

        List<Category> childList = new ArrayList<>();

        for (int i = 1; i < 4; i++ ){
            Category child = Category.builder().categoryName("자식" + i).parent(parent).id(Long.valueOf(i + 1)).depth(2).build();
            parent.addChildCategory(child);
        }

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(parent));


        //when
        ParentCategoryResponse parentCategory = categoryService.getParentCategory(1L);


        //then
        Assertions.assertThat(parentCategory.getChildCategories().size()).isEqualTo(3);
        Assertions.assertThat(parentCategory.getChildCategories().get(0).getName()).isEqualTo("자식1");

    }







}
