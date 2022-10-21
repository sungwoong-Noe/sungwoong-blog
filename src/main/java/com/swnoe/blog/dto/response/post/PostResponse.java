package com.swnoe.blog.dto.response.post;

import com.swnoe.blog.domain.post.Posts;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String thumbnailUrl;
    private CategoryResponse category;



    @Builder
    public PostResponse(Long id, String title, String content, String thumbnailUrl, CategoryResponse category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
    }
}
