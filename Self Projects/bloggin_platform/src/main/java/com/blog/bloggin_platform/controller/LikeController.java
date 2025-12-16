package com.blog.bloggin_platform.controller;


import com.blog.bloggin_platform.model.LikeTable;
import com.blog.bloggin_platform.service.Implementations.LikeServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeServiceImpl likeService;

    public LikeController(
            LikeServiceImpl likeService
    ){
        this.likeService = likeService;
    }

    @PostMapping("/{postId}")
    public LikeTable likePost(
            @PathVariable Long postId,
            @RequestHeader("Authorization") String accessToken
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");

        return   likeService.likePost(postId,token);
    }

    @DeleteMapping("/{postId}/{likeId}")
    public String dislikePost(
            @PathVariable Long postId,
            @PathVariable Long likeId,
            @RequestHeader("Authorization") String accessToken
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");
        return   likeService.dislikePost(postId,likeId,token);
    }


}
