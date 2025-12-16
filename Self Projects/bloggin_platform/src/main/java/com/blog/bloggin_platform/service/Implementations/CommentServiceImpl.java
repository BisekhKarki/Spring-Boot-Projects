package com.blog.bloggin_platform.service.Implementations;

import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.exception.ApiException;
import com.blog.bloggin_platform.exception.JwtException;
import com.blog.bloggin_platform.exception.JwtExpiredException;
import com.blog.bloggin_platform.exception.ResourceNotFoundException;
import com.blog.bloggin_platform.model.Comment;
import com.blog.bloggin_platform.model.Post;
import com.blog.bloggin_platform.model.User;
import com.blog.bloggin_platform.repository.CommentRepository;
import com.blog.bloggin_platform.repository.PostRepository;
import com.blog.bloggin_platform.repository.UserRepository;
import com.blog.bloggin_platform.service.CommentService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final JWT jwt;
    private final UserRepository userRepository;
    private  final CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, JWT jwt, UserRepository userRepository,
    CommentRepository commentRepository
    ){
        this.postRepository = postRepository;
        this.jwt = jwt;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment createAComment(Comment comment, String accessToken, Long postId) throws  Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id", Long.class);
            Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("No post found of ID: "+postId));
            User userDetails = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("No user found of ID: "+postId));;
            Comment newComment = new Comment();
            newComment.setComment(comment.getComment());
            newComment.setPost(post);
            newComment.setUser(userDetails);
            newComment.setCreatedAt(LocalDateTime.now());
            newComment.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(newComment);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw new JwtException("Invalid token");
        }catch (Exception e){
            throw new ApiException("Internal Server Error");
        }
    }

    @Override
    public List<Comment> getAllMyCommentsByPostId(Long postId, String accessToken) throws  Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id", Long.class);
            return commentRepository.findByPost_Id(postId);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw new JwtException("Invalid token");
        }catch (Exception e){
            throw new ApiException("Internal Server Error");
        }

    }

    @Override
    public Comment updateComment(String accessToken, Long commentId, Long postId,String comment) throws  Exception{
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id", Long.class);
            Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("No post found of ID: "+postId));
            User userDetails = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("No user found of ID: "+postId));;
            Comment updatedComment = commentRepository.findByPost_IdAndId(commentId,postId);
            if(!updatedComment.getUser().getId().equals(userId)){
                throw new ApiException("Unauthorized. Please login again!!!");
            }
            updatedComment.setComment(comment);
            updatedComment.setUpdatedAt(LocalDateTime.now());
          return commentRepository.save(updatedComment);
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw new JwtException("Invalid token");
        }catch (Exception e){
            throw new ApiException("Internal Server Error");
        }
    }

    @Override
    public String deleteComment(Long commentId, Long postId, String accessToken) throws  Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id", Long.class);
            Comment updatedComment = commentRepository.findByPost_IdAndId(commentId,postId);
            if(!updatedComment.getUser().getId().equals(userId)){
                throw new ApiException("Unauthorized. Please login again!!!");
            }
            commentRepository.deleteById(commentId);
            return "Comment Deleted Successfully";
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw new JwtException("Invalid token");
        }catch (Exception e){
            throw new ApiException("Internal Server Error");
        }
    }
}
