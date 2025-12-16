package com.blog.bloggin_platform.controller;


import com.blog.bloggin_platform.dto.CommentDto;
import com.blog.bloggin_platform.model.Comment;
import com.blog.bloggin_platform.service.CommentService;
import com.blog.bloggin_platform.service.Implementations.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/create")
    public Comment createComment(
            @RequestBody Comment comment,
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId
            ) throws  Exception {

        String token = accessToken.replace("Bearer ","");
        return  commentService.createAComment(comment,token,postId);
    }

    @GetMapping("/get/{postId}")
    public List<Comment> getComment(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId
    ) throws  Exception {
        String token = accessToken.replace("Bearer ","");
        return  commentService.getAllMyCommentsByPostId(postId,token);
    }

    @PatchMapping("/{postId}/update/{commentId}")
    public Comment updateComment(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDto comment
            ) throws  Exception {
        String token = accessToken.replace("Bearer ","");
        return  commentService.updateComment(token,commentId,postId,comment.getComment());
    }


    @DeleteMapping("/{postId}/delete/{commentId}")
    public String deleteComment(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) throws  Exception {
        String token = accessToken.replace("Bearer ","");
        return  commentService.deleteComment(commentId,postId,token);
    }

}

