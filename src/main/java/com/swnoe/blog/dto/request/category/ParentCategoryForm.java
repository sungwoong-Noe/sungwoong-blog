package com.swnoe.blog.dto.request.category;


import com.swnoe.blog.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCategoryForm {


    private String name;
    private int depth;

    @Builder
    public ParentCategoryForm(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    public Category toEntity(){
        return Category.builder()
                .categoryName(this.name)
                .depth(this.depth)
                .build();
    }
}
