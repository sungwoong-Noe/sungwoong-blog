package com.swnoe.blog.exception;

public class CategoryNotFound extends IllegalArgumentException{

    public CategoryNotFound(String message) {
        super(message);
    }
}
