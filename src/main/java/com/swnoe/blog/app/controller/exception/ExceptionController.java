package com.swnoe.blog.app.controller.exception;

import com.swnoe.blog.dto.response.error.ErrorResponse;
import com.swnoe.blog.exception.CategoryDeleteFailed;
import com.swnoe.blog.exception.CategoryNotFound;
import com.swnoe.blog.exception.PostNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidResponseBuilder(MethodArgumentNotValidException e){

        ErrorResponse.ErrorResponseBuilder response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.");

        Map<String, String> validation = new HashMap<>();

        for(FieldError fieldError: e.getFieldErrors()){
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response.validation(validation).build();
    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse categoryNotFound(CategoryNotFound e){

        ErrorResponse response = ErrorResponse.builder()
                .message(e.getMessage())
                .code("400")
                .build();

        return response;
    }

    @ExceptionHandler(PostNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String postNotFound(PostNotFound e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "exception/400";
    }

    @ExceptionHandler(CategoryDeleteFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse categoryDeleteFailed(CategoryDeleteFailed e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return response;
    }
}
