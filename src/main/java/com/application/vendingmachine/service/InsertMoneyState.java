package com.application.vendingmachine.service;

public class InsertMoneyState extends VendingMachineState{
    public InsertMoneyState(VendingMachine instance) {
        super(instance);
    }

    public String selection(){
        return "Please complete the transaction! before starting another";
    }

    public String insertMoney(int denomination){
        vmInstance.addMoney(denomination);
        if(vmInstance.isMoneySufficient(denomination)){
            if(vmInstance.isReturnPossible()){
                vmInstance.setDispenseState();
                vmInstance.getState().dispense();
            }
            else{
                return "Internal Error!! Cancel the transaction";
            }
        }
        return "Please add remaining " + vmInstance.getRemainingPrice() + " to complete the transaction";
    }
    public String cancel(){
        return "";
    }
}
