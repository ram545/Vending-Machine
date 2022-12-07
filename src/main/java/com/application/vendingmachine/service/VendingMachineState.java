package com.application.vendingmachine.service;

public abstract class VendingMachineState {
    static VendingMachine vmInstance;

    public VendingMachineState(VendingMachine instance) {
        this.vmInstance = instance;
    }

    public String selection(long id){
        return false;
    }
    public String insertMoney(int denomination){
        return "";
    }
    public String dispense(){

        return "";
    }
    public String cancel(){

        return false;
    }
}
