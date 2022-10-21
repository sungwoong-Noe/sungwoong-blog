package com.swnoe.blog.domain.post;

import com.swnoe.blog.domain.base.BaseEntity;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.response.post.PostResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends BaseEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String thumbnailUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @Builder
    public Posts(String title, String content, String thumbnailUrl) {
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }

    public PostResponse toResponseDto(){
        return PostResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .thumbnailUrl(this.thumbnailUrl)
                .category(this.category.toResponseDto())
                .build();
    }

    //카테고리 연관관계 편의 메서드
    public void  setCategory(Category category){
        if(this.category != null){
            this.category.getPosts().remove(this);
        }

        this.category = category;
        category.getPosts().add(this);
    }

}
