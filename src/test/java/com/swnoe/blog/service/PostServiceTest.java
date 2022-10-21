package com.swnoe.blog.service;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.app.service.post.PostService;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.domain.post.Posts;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.app.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;


//    @BeforeEach
//    void clean(){
//        postRepository.deleteAll();
//    }

    @Test
    @DisplayName("게시글 저장")
    void 게시글_저장(){

        //given
        PostRequest request = PostRequest.builder()
                .title("제목1")
                .content("게시글1")
                .build();

        //when
        postService.savePost(request);
        List<Posts> allPost = postRepository.findAll();

        //then
        Assertions.assertThat(1).isEqualTo(postRepository.count());
        Assertions.assertThat(allPost.get(0).getTitle()).isEqualTo("제목1");
        Assertions.assertThat(allPost.get(0).getContent()).isEqualTo("게시글1");
    }

    @Test
    @Transactional
    void 매핑된Entity조회() {

        //given
        Posts posts = postRepository.findById(4L).get();
        Category category2 = categoryRepository.findById(2L).get();

        //when
        Category postsCategory = posts.getCategory();

        //then
        System.out.println(category2.getName());
        System.out.println(postsCategory.getName());
        Assertions.assertThat(category2.getName()).isEqualTo(postsCategory.getName());
    }
}
