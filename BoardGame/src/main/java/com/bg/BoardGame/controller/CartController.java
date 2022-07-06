package com.bg.BoardGame.controller;

import com.bg.BoardGame.common.ApiResponse;
import com.bg.BoardGame.dto.cart.AddToCartDto;
import com.bg.BoardGame.dto.cart.CartDto;
import com.bg.BoardGame.model.Game;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.service.AuthenticationService;
import com.bg.BoardGame.service.CartService;
import com.bg.BoardGame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    AuthenticationService authenticationService;


    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) {
        //authenticate the token
        authenticationService.getUser(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true,"Added to cart"), HttpStatus.OK);
    }

    //get all cart items for user
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        //authenticate the token
        authenticationService.getUser(token);

        //find the user
        User user = authenticationService.getUser(token);

        //get cart items
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //delete cart item
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token) {
        //authenticate the token
        authenticationService.getUser(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.removeCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true,"Removed from the cart"), HttpStatus.OK);
    }
}
