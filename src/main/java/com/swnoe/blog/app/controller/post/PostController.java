package com.swnoe.blog.app.controller.post;

import com.swnoe.blog.app.service.category.CategoryService;
import com.swnoe.blog.dto.request.post.PostRequest;
import com.swnoe.blog.dto.response.post.PostResponse;
import com.swnoe.blog.app.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model){
        List<PostResponse> postList = postService.postList();
        model.addAttribute("postList", postList);
        return "posts/postList";
    }

    @GetMapping("/write")
    public String writePage(Model model){
        model.addAttribute("postRequest", new PostRequest());
        return "posts/writePost";
    }

    @PostMapping("/write")
    public String write(@Validated @ModelAttribute PostRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "posts/writePost";
        }

        PostResponse postResponse = postService.savePost(request);
        return "redirect:/post/" + postResponse.getId();
    }

    @GetMapping("/post/{postId}")
    public String postPage(@PathVariable Long postId, Model model){

        PostResponse response = postService.findById(postId);

        model.addAttribute("posts", response);
        return "posts/post";
    }

//    @GetMapping("/post/{postId}")
//    @ResponseBody
    //    public PostResponse getPost(@PathVariable Long postId){
//        PostResponse response = postService.findById(postId);
//        return response;
//    }

//    @PostMapping("/post")
//    @ResponseBody
//    public Long savePost(@RequestBody @Valid PostRequest postRequest){
//        Long postId = postService.savePost(postRequest);
//        return postId;
//    }

}
