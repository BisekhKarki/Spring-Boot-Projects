package com.blog.bloggin_platform.service.Implementations;

import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.exception.ApiException;
import com.blog.bloggin_platform.exception.JwtException;
import com.blog.bloggin_platform.exception.JwtExpiredException;
import com.blog.bloggin_platform.model.LikeTable;
import com.blog.bloggin_platform.model.Post;
import com.blog.bloggin_platform.model.User;
import com.blog.bloggin_platform.repository.LikeRepository;
import com.blog.bloggin_platform.repository.PostRepository;
import com.blog.bloggin_platform.repository.UserRepository;
import com.blog.bloggin_platform.service.LikeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl  implements LikeService {

    private final PostRepository postRepository;
    private final JWT jwt;
    private final UserRepository userRepository;
    private  final LikeRepository likeRepository;

    public LikeServiceImpl(PostRepository postRepository, JWT jwt, UserRepository userRepository,
                              LikeRepository likeRepository
    ){
        this.postRepository = postRepository;
        this.jwt = jwt;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public LikeTable likePost(Long postId, String accessToken) throws  Exception {
       try{
           Claims token = jwt.decodeToken(accessToken);
           Long id = token.get("id",Long.class);
           User userDetails = userRepository.findById(id).orElseThrow(()->  new JwtExpiredException("Invalid token"));
           Post post = postRepository.findById(postId).orElseThrow(()->  new JwtExpiredException("Invalid token"));
           LikeTable liked = new LikeTable();
           liked.setPost(post);
           liked.setUser(userDetails);
           liked.setCreatedAt(LocalDateTime.now());
           return likeRepository.save(liked);
       }catch (ExpiredJwtException expiredJwtException){
           throw  new JwtExpiredException("Token has expired");
       }catch (JwtException jwtException){
           throw  new JwtException("Invalid Token");
       }catch (Exception e){
           throw  new ApiException("Internal Server Error");
       }
    }

    @Override
    public String dislikePost(Long postId, Long likeId, String accessToken) throws  Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long id = token.get("id",Long.class);
            LikeTable liked = likeRepository.findById(likeId).orElseThrow(()->  new JwtExpiredException("Invalid token"));
            if(!liked.getUser().getId().equals(id) || !liked.getPost().getId().equals(postId)){
                throw  new ApiException("Unauthorized. Please login again!!!");
            }
            likeRepository.deleteById(liked.getId());
            return "Post unliked";
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired");
        }catch (JwtException jwtException){
            throw  new JwtException("Invalid Token");
        }catch (Exception e){
            throw  new ApiException("Internal Server Error");
        }
    }
}
