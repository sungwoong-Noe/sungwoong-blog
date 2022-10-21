package com.swnoe.blog.dto.response.category;

import com.swnoe.blog.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parent;

@Getter @Setter
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private Long parentId;
    private int depth;

    @Builder
    public CategoryResponse(Long id, String name,Long parentId, int depth) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
    }
}
