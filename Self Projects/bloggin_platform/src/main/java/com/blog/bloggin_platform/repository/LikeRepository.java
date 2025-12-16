package com.blog.bloggin_platform.repository;

import com.blog.bloggin_platform.model.LikeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeTable,Long> {
    LikeTable findLikeByPostId(Long postId);
}
