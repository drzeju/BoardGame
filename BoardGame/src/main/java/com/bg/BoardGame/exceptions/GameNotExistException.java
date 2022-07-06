package com.bg.BoardGame.exceptions;

public class GameNotExistException extends IllegalArgumentException {
    public GameNotExistException(String s) {
        super(s);
    }
}
