package com.ecommerce.Ecommerce.Beginner.projects.controller;


import com.ecommerce.Ecommerce.Beginner.projects.dto.CartDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.Cart;
import com.ecommerce.Ecommerce.Beginner.projects.response.Response;
import com.ecommerce.Ecommerce.Beginner.projects.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMyCart(
            @RequestHeader("Authorization") String accessToken
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");
        List<Cart> carts = cartService.getAllMyCart(token);
        if(carts.isEmpty()){
            return ResponseEntity.status(400).body(new Response(400,"No items in your cart",null));

        }
       return ResponseEntity.status(200).body(new Response(200,"List of your cart items",carts));
    }

    @PostMapping("/{productId}/add")
    public ResponseEntity<?> addItemsToCart(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody  CartDto cartDto,
             @PathVariable Long productId
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");
        Cart carts = cartService.addItemsToCart(token,productId,cartDto);
        if(carts== null){
            return ResponseEntity.status(400).body(new Response(400,"Failed to add items to the cart",null));
        }
        return ResponseEntity.status(201).body(new Response(201,"Items added to your cart",carts));
    }

    @PatchMapping("/{cartId}/update")
    public ResponseEntity<?> updateItemsToCart(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody  CartDto cartDto,
            @PathVariable Long cartId
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");
        Cart carts = cartService.updateItemsToCart(token,cartId,cartDto);
        if(carts== null){
            return ResponseEntity.status(400).body(new Response(400,"Failed to update items oo the cart",null));
        }
        return ResponseEntity.status(200).body(new Response(200,"Items updated",carts));
    }

    @DeleteMapping("/{cartId}/delete")
    public ResponseEntity<?> deleteItem(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long cartId
    ) throws Exception {
        String token = accessToken.replace("Bearer ","");
        String carts = cartService.deleteItem(token,cartId);
        if(carts.isEmpty()){
            return ResponseEntity.status(400).body(new Response(400,"Failed to delete items from the cart",null));
        }
        return ResponseEntity.status(403).body(new Response(403,carts,null));

    }
}
