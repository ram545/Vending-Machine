package com.application.vendingmachine.service;

public class SelectionState extends VendingMachineState{

    public SelectionState(VendingMachine instance) {
        super(instance);
    }

    public String selection(long id){
        if(vmInstance.isSelectionValid(id)){
            if(vmInstance.inStock()){
                vmInstance.setInsertState();
                return "Please insert " + vmInstance.getRemainingPrice() + " to complete the transaction";
            }
            return "The requested item is not item ! please choose a different option";
        }
        return "Please choose and existing option on screen";
    }

    public String insertMoney(int denomination){
        return "Please select an option! before inserting money";
    }

    public String cancel(){
        vmInstance.setCancelState();
        return vmInstance.getState().cancel();
    }
}
