package com.gearfound.itemservice.web;


import com.gearfound.itemservice.items.LostItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(LostItemNotFoundException.class)
    public void handleNotFound() {
        //
    }
}
