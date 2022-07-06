package com.bg.BoardGame.dto.cart;


import com.bg.BoardGame.model.Cart;
import com.bg.BoardGame.model.Game;

public class CartItemDto {
    private Integer id;
    private Integer quantity;
    private Game game;

    public CartItemDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setGame(cart.getGame());
    }
}
