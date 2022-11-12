package com.swnoe.blog.exception;

public class CategoryDeleteFailed extends RuntimeException{

    public CategoryDeleteFailed(String message) {
        super(message);
    }
}
