package com.application.vendingmachine.exception;


//triggered when no such item exists in our inventory
public class NoSuchItemException extends Throwable{

    public NoSuchItemException(String error){
        super(error);
    }

}
