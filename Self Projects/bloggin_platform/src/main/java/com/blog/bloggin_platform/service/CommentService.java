package com.blog.bloggin_platform.service;

import com.blog.bloggin_platform.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment createAComment(Comment comment, String accessToken, Long postId) throws  Exception;
    List<Comment> getAllMyCommentsByPostId(Long postId, String accessToken) throws  Exception;
    Comment updateComment(String accessToken ,Long commentId, Long postId, String comment) throws  Exception;
    String deleteComment(Long commentId,Long postId, String accessToken) throws  Exception;
}
