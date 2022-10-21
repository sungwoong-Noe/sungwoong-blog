package com.swnoe.blog.dto.response.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCategoryResponse {

    private Long id;
    private String name;
    private int depth;

}
