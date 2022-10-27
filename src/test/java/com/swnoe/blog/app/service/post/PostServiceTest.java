package com.swnoe.blog.app.service.post;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.app.service.category.CategoryService;
import com.swnoe.blog.app.service.post.PostService;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.domain.post.Posts;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.app.repository.PostRepository;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import com.swnoe.blog.dto.response.post.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock   //실제 객체 대신 사용하게될 클래스
    PostRepository postRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks    //Mock객체를 사용할 클래스
    PostService postService;

    @Test
    @DisplayName("글 등록")
    void savePost(){

        //given
        Category category = Category.builder()
                .depth(0)
                .categoryName("임시")
                .build();

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));

        PostRequest request = PostRequest.builder()
                .title("제목")
                .content("내용")
                .thumbnailUrl("섬네[일")
                .categoryId(1L)
                .build();
        Posts posts = request.toEntity();
        posts.setCategory(category);

        when(postRepository.save(any())).thenReturn(posts);

        //when
        PostResponse postResponse = postService.savePost(request);

        //then
        Assertions.assertThat(postResponse.getTitle()).isEqualTo(request.getTitle());
        Assertions.assertThat(postResponse.getContent()).isEqualTo(request.getContent());
        Assertions.assertThat(postResponse.getCategory().getName()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("게시글 리스트")
    void postList(){

        //given
        PostRequest request1 = PostRequest.builder()
                .title("제목1")
                .content("내용1")
                .thumbnailUrl("섬네일")
                .build();

        PostRequest request2 = PostRequest.builder()
                .title("제목2")
                .content("내용2")
                .thumbnailUrl("섬네일")
                .build();

        Category category = Category.builder()
                .depth(0)
                .categoryName("임시")
                .build();

        Posts post1 = request1.toEntity();
        post1.setCategory(category);

        Posts post2 = request2.toEntity();
        post2.setCategory(category);

        List<Posts> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);



        when(postRepository.findAll()).thenReturn(postList);


        //when
        List<PostResponse> postResponses = postService.postList();


        //then
        Assertions.assertThat(postResponses.size()).isEqualTo(postList.size());
        Assertions.assertThat(postResponses.get(0).getTitle()).isEqualTo(postList.get(0).getTitle());
        Assertions.assertThat(postResponses.get(0).getContent()).isEqualTo(postList.get(0).getContent());
    }


    @Test
    @DisplayName("조회 -id로 조회하기")
    void findById(){
        //given
        Posts post = Posts.builder()
                .title("제목")
                .content("내용")
                .thumbnailUrl("thumb")
                .build();

        Category category = Category.builder()
                .depth(0)
                .categoryName("임시")
                .build();

        post.setCategory(category);

        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        //when
        PostResponse response = postService.findById(1L);

        //then
        Assertions.assertThat(response.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(response.getContent()).isEqualTo(post.getContent());
        Assertions.assertThat(response.getThumbnailUrl()).isEqualTo(post.getThumbnailUrl());
        Assertions.assertThat(response.getCategory().getName()).isEqualTo(category.getName());
    }
}
