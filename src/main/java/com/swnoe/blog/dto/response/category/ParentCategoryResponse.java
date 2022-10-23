package com.swnoe.blog.dto.response.category;

import com.swnoe.blog.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParentCategoryResponse {

    private Long id;
    private String name;
    private int depth;
    private List<CategoryResponse> childCategories;


    @Builder
    public ParentCategoryResponse(Long id, String name, int depth, List<CategoryResponse> childCategories) {
        this.id = id;
        this.name = name;
        this.depth = depth;
        this.childCategories = childCategories;
    }


}
