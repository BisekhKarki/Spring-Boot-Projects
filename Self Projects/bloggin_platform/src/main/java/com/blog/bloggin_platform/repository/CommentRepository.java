package com.blog.bloggin_platform.repository;

import com.blog.bloggin_platform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long postId);

    Comment findByPost_IdAndId(Long commentId,Long postId);
}
