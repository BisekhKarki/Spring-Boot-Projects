package com.blog.bloggin_platform.service.Implementations;

import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.dto.AuthorDto;
import com.blog.bloggin_platform.dto.PostDto;
import com.blog.bloggin_platform.exception.ApiException;
import com.blog.bloggin_platform.exception.JwtException;
import com.blog.bloggin_platform.exception.JwtExpiredException;
import com.blog.bloggin_platform.exception.ResourceNotFoundException;
import com.blog.bloggin_platform.model.Post;
import com.blog.bloggin_platform.model.User;
import com.blog.bloggin_platform.repository.PostRepository;
import com.blog.bloggin_platform.repository.UserRepository;
import com.blog.bloggin_platform.service.PostService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final JWT jwt;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, JWT jwt, UserRepository userRepository){
        this.postRepository = postRepository;
        this.jwt = jwt;
        this.userRepository = userRepository;
    }


    @Override
    public List<Post> getAllMyPosts(String accessToken) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            return postRepository.findByAuthorId(userId);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new RuntimeException("Invalid token");
        }

    }

    @Override
    public Post getPostByPostId(String accessToken, Long postId) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            return postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("No post found with id: "+postId));
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new RuntimeException("Invalid token");
        }
    }

    @Override
    public Post createPost(String accessToken, PostDto post) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            User userDetails = userRepository.findById(userId).orElseThrow(()-> new JwtExpiredException("Invalid token"));

            Post newPost = new Post();
            newPost.setAuthor(userDetails);
            newPost.setTitle(post.getTitle());
            newPost.setContent(post.getContent());
            newPost.setCreatedAt(LocalDateTime.now());
            newPost.setUpdatedAt(LocalDateTime.now());

            return postRepository.save(newPost);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new RuntimeException("Invalid token");
        }
    }

    @Override
    public Post updatePost(PostDto postDto,String accessToken, Long postId) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            User userDetails = userRepository.findById(userId).orElseThrow(()-> new JwtExpiredException("Invalid token"));
            Post existingPost = postRepository.findById(postId).orElseThrow(  () -> new ApiException("No post found of ID: "+ postId));

            if(!existingPost.getAuthor().getId().equals(userId)){
                    throw  new ApiException("You do not have permission to update this post");
            }

            existingPost.setTitle(postDto.getTitle());
            existingPost.setContent(postDto.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());

            return postRepository.save(existingPost);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new RuntimeException("Invalid token");
        }

    }

    @Override
    public String deletePost(String accessToken, Long postId) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            User userDetails = userRepository.findById(userId).orElseThrow(()-> new JwtExpiredException("Invalid token"));
            Post existingPost = postRepository.findById(postId).orElseThrow(  () -> new ApiException("No post found of ID: "+ postId));
            if(!existingPost.getAuthor().getId().equals(userId)){
                throw  new ApiException("You do not have permission to update this post");
            }
            postRepository.deleteById(postId);
            return "Post Deleted Successfully";
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new RuntimeException("Invalid token");
        }
    }
}
