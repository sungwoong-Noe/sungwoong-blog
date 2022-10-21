package com.swnoe.blog.dto.request.category;

import com.swnoe.blog.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRegistForm {

    private String name;
    private Long parentId;
    private int depth;


    @Builder
    public CategoryRegistForm(String name, Long parentId, int depth) {
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
    }
}
