package com.swnoe.blog.app.service.category;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final int CHILD_CATEGORY_DEPTH = 2;
    private final int PARENT_CATEGORY_DEPTH = 1;

    private final CategoryRepository categoryRepository;



    @Transactional
    public CategoryResponse regist(CategoryRegistForm registForm){

        Category categoryForm;
        CategoryResponse response;

        Category.CategoryBuilder categoryFormBuilder = Category.builder()
                                                .categoryName(registForm.getName());


        if(registForm.getParentId() != null){
            Category parentCategory = categoryRepository.findById(registForm.getParentId()).get();
            categoryForm = categoryFormBuilder.depth(CHILD_CATEGORY_DEPTH).build();
            parentCategory.addChildCategory(categoryForm);
        }else{
            categoryForm = categoryFormBuilder.depth(PARENT_CATEGORY_DEPTH).build();
        }

        Category saveCategory = categoryRepository.save(categoryForm);

        return saveCategory.toResponseDto();
    }

    public List<CategoryResponse> parentCategoryList(){

        List<CategoryResponse> parentCategoryResponseList  = categoryRepository.findCategoryByParentIsNull().stream()
                                                                                .map(parent -> {
                                                                                    return parent.toResponseDto();
                                                                                }).collect(Collectors.toList());
        return parentCategoryResponseList;
    }
}
