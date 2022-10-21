package com.swnoe.blog.dto.request.post;

import com.swnoe.blog.domain.post.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "제목")
    private String title;

    @NotBlank(message = "내용")
    private String content;

    @NotBlank(message = "썸네일")
    private String thumbnailUrl;

    private Long categoryId;


//    @NotBlank(message = "작성자 정보가 없습니다.")
//    private String author;

    public Posts toEntity(){
        return Posts.builder()
                .title(this.title)
                .content(this.content)
                .thumbnailUrl(this.thumbnailUrl)
                .build();
    }

    @Builder
    public PostRequest(String title, String content, String thumbnailUrl, Long categoryId) {
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.categoryId = categoryId;
    }
}
