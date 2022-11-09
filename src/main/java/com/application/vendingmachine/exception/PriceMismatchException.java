package com.application.vendingmachine.exception;

public class PriceMismatchException extends Throwable{

    public PriceMismatchException(String error){
        super(error);
    }
}
