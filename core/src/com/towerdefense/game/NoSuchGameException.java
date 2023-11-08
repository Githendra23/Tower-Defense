package com.towerdefense.game;

public class NoSuchGameException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoSuchGameException(String message) {
        super(message);
    }
}
