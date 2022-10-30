package com.swnoe.blog.app.service.post;

import com.swnoe.blog.app.repository.CategoryRepository;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.domain.post.Posts;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.dto.response.post.PostResponse;
import com.swnoe.blog.app.repository.PostRepository;
import com.swnoe.blog.exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final static int TEMPORARY_CATEGORY_SEQ = 0;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;


    public List<PostResponse> postList(){

        List<Posts> postList = postRepository.findAll();

        return postList.stream().map(p -> p.toResponseDto()).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse savePost(PostRequest postRequest){

        Posts post = postRequest.toEntity();

        Category category;

        if(postRequest.getCategoryId() == null){
            category = categoryRepository.findByDepth(TEMPORARY_CATEGORY_SEQ).get(0);
        } else{
            category = categoryRepository.findById(postRequest.getCategoryId()).get();
        }

        post.setCategory(category);
        Posts savedPost = postRepository.save(post);

        return savedPost.toResponseDto();
    }


    public PostResponse findById(Long postId){

        Posts posts = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("일치하는 게시글이 없습니다"));

        PostResponse postResponse = posts.toResponseDto();

        return postResponse;
    }

}
