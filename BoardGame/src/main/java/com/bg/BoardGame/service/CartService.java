package com.bg.BoardGame.service;

import com.bg.BoardGame.dto.cart.AddToCartDto;
import com.bg.BoardGame.dto.cart.CartDto;
import com.bg.BoardGame.dto.cart.CartItemDto;
import com.bg.BoardGame.exceptions.CustomException;
import com.bg.BoardGame.model.Cart;
import com.bg.BoardGame.model.Game;
import com.bg.BoardGame.model.User;
import com.bg.BoardGame.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    GameService gameService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        //validate if product id is valid
        Game game = gameService.findById(addToCartDto.getGameId());

        Cart cart = new Cart();
        cart.setGame(game);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        //save the cart
        cartRepository.save(cart);
    }


    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getGame().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);

        return cartDto;
    }


    public void removeCartItem(Integer cartItemId, User user) {
        //check if item id belong to user
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if(optionalCart.isEmpty()) {
            throw new CustomException("cart item id is not valid " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("cart item does not belong to user: " + cartItemId);
        }

        cartRepository.delete(cart);
    }
}
