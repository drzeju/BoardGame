package com.bg.BoardGame.controller;

import com.bg.BoardGame.common.ApiResponse;
import com.bg.BoardGame.dto.cart.AddToCartDto;
import com.bg.BoardGame.dto.cart.CartDto;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.service.AuthenticationService;
import com.bg.BoardGame.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
//@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
@RequestMapping("/user")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    AuthenticationService authenticationService;


    //post cart api
    @PostMapping("/cart/add")
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
    @GetMapping("/cart")
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
    @DeleteMapping("/cart/remove/{cartItemId}")
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
