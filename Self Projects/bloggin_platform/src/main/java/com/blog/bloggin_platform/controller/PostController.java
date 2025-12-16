package com.blog.bloggin_platform.controller;


import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.dto.PostDto;
import com.blog.bloggin_platform.model.Post;
import com.blog.bloggin_platform.repository.PostRepository;
import com.blog.bloggin_platform.service.Implementations.PostServiceImpl;
import com.blog.bloggin_platform.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    public  PostController(
            PostService postService,
            PostServiceImpl postServiceImpl,
            PostRepository postRepository,
            JWT jwt
    ){
        this.postServiceImpl = postServiceImpl;
    }


    @PostMapping("/create")
    public Post createPost(
            @RequestBody PostDto post,
            @RequestHeader("Authorization") String accessToken
    ) throws  Exception {
        String token = accessToken.replace("Bearer ","");
        return  postServiceImpl.createPost(token,post);
    }

    @GetMapping
    public List<Post> getAllPosts(
            @RequestHeader("Authorization") String accessToken
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        return  postServiceImpl.getAllMyPosts(token);
    }

    @GetMapping("/{postId}")
    public Post getPostById(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        return  postServiceImpl.getPostByPostId(token, postId);
    }

    @PatchMapping("/{postId}")
    public Post updatePost(
            @RequestBody PostDto postDto,
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        return  postServiceImpl.updatePost(postDto,token, postId);
    }


    @DeleteMapping("/{postId}")
    public String deletePost(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId
    )throws  Exception {
        String token = accessToken.replace("Bearer ","");
        return postServiceImpl.deletePost(token,postId);
    }

}
