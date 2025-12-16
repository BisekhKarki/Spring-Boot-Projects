package com.ecommerce.Ecommerce.Beginner.projects.service.Impl;

import com.ecommerce.Ecommerce.Beginner.projects.components.JWT;
import com.ecommerce.Ecommerce.Beginner.projects.dto.CartDto;
import com.ecommerce.Ecommerce.Beginner.projects.exception.ApiException;
import com.ecommerce.Ecommerce.Beginner.projects.exception.JwtExpired;
import com.ecommerce.Ecommerce.Beginner.projects.model.Cart;
import com.ecommerce.Ecommerce.Beginner.projects.model.Product;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import com.ecommerce.Ecommerce.Beginner.projects.repository.CartRepository;
import com.ecommerce.Ecommerce.Beginner.projects.repository.ProductRepository;
import com.ecommerce.Ecommerce.Beginner.projects.repository.UserRepository;
import com.ecommerce.Ecommerce.Beginner.projects.service.CartService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartImpl implements CartService {

    private final JWT jwt;
    private  final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public  CartImpl(JWT jwt, ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository){
        this.jwt = jwt;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public List<Cart> getAllMyCart(String accessToken) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("userId",Long.class);
            return cartRepository.findAll().
                    stream()
                    .filter(cart-> cart.getUser().getId().equals(userId))
                    .toList();
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public Cart addItemsToCart(String accessToken, Long productId, CartDto cartDto) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("userId",Long.class);
            UserModel userDetails = userRepository.findById(userId).orElseThrow(()-> new ApiException("No user found with id: "+userId));
            Product productDetails = productRepository.findById(productId).orElseThrow(()-> new ApiException("No product found with id: "+productId));
            if(userDetails.getId().equals(productDetails.getOwner().getId())){
                throw new ApiException("Owner cannot set items to the cart");
            }
            Cart newItem = new Cart();
            newItem.setItem(productDetails);
            newItem.setUser(userDetails);
            newItem.setQuantity(cartDto.getQuantity());
            newItem.setTotalPrice(cartDto.getTotalPrice());
            newItem.setCreatedAt(LocalDateTime.now());
            newItem.setUpdatedAt(LocalDateTime.now());
            return cartRepository.save(newItem);
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

    }

    @Override
    public Cart updateItemsToCart(String accessToken, Long cartId, CartDto cartDto) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("userId",Long.class);
            UserModel userDetails = userRepository.findById(userId).orElseThrow(()-> new ApiException("No user found with id: "+userId));
            Cart cartItem = cartRepository.findById(cartId).orElseThrow(()-> new ApiException("No cart found with id: "+cartId));
            if(!userDetails.getId().equals(cartItem.getUser().getId())){
                throw new JwtExpired("Unauthorized...");
            }
            cartItem.setQuantity(cartDto.getQuantity());
            cartItem.setTotalPrice(cartDto.getTotalPrice());
            cartItem.setUpdatedAt(LocalDateTime.now());
            return cartRepository.save(cartItem);
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public String deleteItem(String accessToken, Long cartId) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("userId",Long.class);
            UserModel userDetails = userRepository.findById(userId).orElseThrow(()-> new ApiException("No user found with id: "+userId));
            Cart cartItem = cartRepository.findById(cartId).orElseThrow(()-> new ApiException("No cart found with id: "+cartId));
            if(!userDetails.getId().equals(cartItem.getUser().getId())){
                throw new JwtExpired("Unauthorized...");
            }
            cartRepository.deleteById(cartId);
            return "Items deleted successfully";
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
