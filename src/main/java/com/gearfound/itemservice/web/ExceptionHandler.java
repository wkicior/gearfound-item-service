package com.gearfound.itemservice.web;


import com.gearfound.itemservice.items.found.FoundItemNotFoundException;
import com.gearfound.itemservice.items.found.NoAccessToFoundItemException;
import com.gearfound.itemservice.items.lost.LostItemIdDoesNotMatchException;
import com.gearfound.itemservice.items.lost.LostItemNotFoundException;
import com.gearfound.itemservice.items.lost.NoAccessToLostItemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(LostItemNotFoundException.class)
    public void handleNotFoundLostItem() {
        //
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(FoundItemNotFoundException.class)
    public void handleNotFoundFoundItem() {
        //
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(NoAccessToLostItemException.class)
    public void handleForbiddenLostItem() {
        //
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(NoAccessToFoundItemException.class)
    public void handleForbiddenFoundItem() {
        //
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(LostItemIdDoesNotMatchException.class)
    public void handleLostItemIdDoesNotMatchException() {
        //
    }
}
