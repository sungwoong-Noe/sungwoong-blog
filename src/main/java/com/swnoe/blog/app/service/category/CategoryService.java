package com.swnoe.blog.app.service.category;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.request.category.ParentCategoryForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.category.ParentCategoryResponse;
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

        List<CategoryResponse> parentCategoryResponseList  = categoryRepository.findCategoryByParentIsNullAndDepth(PARENT_CATEGORY_DEPTH).stream()
                                                                                .map(parent -> {
                                                                                    return parent.toResponseDto();
                                                                                }).collect(Collectors.toList());
        return parentCategoryResponseList;
    }

    @Transactional
    public ParentCategoryResponse getParentCategory(Long parentId){
        Category parentCategory = categoryRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("조회된 카테고리 없음"));
        List<Category> childCategories = parentCategory.getChild();

        List<CategoryResponse> childResponseCategories = childCategories.stream()
                .map(child -> CategoryResponse.builder()
                        .id(child.getId())
                        .name(child.getName())
                        .parentId(parentId)
                        .depth(child.getDepth())
                        .build())
                .collect(Collectors.toList());

        ParentCategoryResponse parentCategoryResponse = parentCategory.toParentResponseDTO(childResponseCategories);

        return parentCategoryResponse;
    }





}
