package com.blog.bloggin_platform.repository;

import com.blog.bloggin_platform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long id);
}
