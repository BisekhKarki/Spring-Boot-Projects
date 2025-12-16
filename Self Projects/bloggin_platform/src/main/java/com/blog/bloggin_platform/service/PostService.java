package com.blog.bloggin_platform.service;

import com.blog.bloggin_platform.dto.PostDto;
import com.blog.bloggin_platform.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {


    List<Post> getAllMyPosts(String token) throws  Exception;
    Post getPostByPostId(String token, Long postId) throws  Exception;
    Post createPost(String token,PostDto post) throws  Exception;
    Post updatePost(PostDto postDto,String accessToken, Long postId) throws  Exception;
    String deletePost(String token,Long postId) throws  Exception;
}
