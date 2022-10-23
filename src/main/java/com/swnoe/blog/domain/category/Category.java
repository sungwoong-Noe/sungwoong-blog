package com.swnoe.blog.domain.category;

import com.swnoe.blog.domain.base.BaseEntity;
import com.swnoe.blog.domain.post.Posts;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.category.ParentCategoryResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int depth;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PARENT_ID")
    private Category parent;


    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Posts> posts = new ArrayList<>();


    @Builder
    public Category(String categoryName, Category parent, int depth){
        this.name = categoryName;
        this.parent = parent;
        this.depth = depth;
    }

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

    public void setParent(Category parent){
        this.parent = parent;
    }


    //Category -> CategoryResponse
    public CategoryResponse toResponseDto(){
        CategoryResponse.CategoryResponseBuilder responseBuilder = CategoryResponse.builder()
                .id(this.id)
                .name(this.name)
                .depth(this.depth);

        if(this.parent != null){
            return responseBuilder.parentId(this.parent.getId()).build();
        }else{
            return responseBuilder.parentId(null).build();
        }
    }

    //Category(Parent) -> ParentCategoryResponse
    public ParentCategoryResponse toParentResponseDTO(List<CategoryResponse> childCategories){
        return ParentCategoryResponse.builder()
                .id(this.id)
                .name(this.name)
                .depth(this.depth)
                .childCategories(childCategories)
                .build();


    }
}

