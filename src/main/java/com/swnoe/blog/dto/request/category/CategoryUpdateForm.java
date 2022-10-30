package com.swnoe.blog.dto.request.category;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateForm {

    private Long id;
    private String name;
    private Long parentId;

    @Builder
    public CategoryUpdateForm(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

}
