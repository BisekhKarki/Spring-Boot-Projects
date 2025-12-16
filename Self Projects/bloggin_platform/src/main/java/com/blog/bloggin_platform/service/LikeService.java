package com.blog.bloggin_platform.service;

import com.blog.bloggin_platform.model.LikeTable;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    LikeTable likePost(Long postId, String accessToken) throws  Exception;
    String dislikePost(Long postId,Long likeId, String accessToken) throws  Exception;

}
