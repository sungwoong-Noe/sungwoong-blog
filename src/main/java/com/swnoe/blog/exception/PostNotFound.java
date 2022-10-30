package com.swnoe.blog.exception;

public class PostNotFound extends RuntimeException{

    public PostNotFound(String message) {
        super(message);
    }
}
