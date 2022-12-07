package com.application.vendingmachine.service;

import com.application.vendingmachine.exception.NoSuchDenominationException;
import com.application.vendingmachine.model.VmInvModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendingMachine {

    @Autowired
    private VmMoneyService moneyHandler;
    @Autowired
    private VmInvService invHandler;

    private VendingMachineState state;
    private SelectionState selectState;
    private InsertMoneyState insertState;
    private DispenseState dispenseState;
    private CancelState cancelState;
    private HashMap<Integer, VmInvModel> invList;

    private VmInvModel selectedItem;

    private double moneyInserted;
    private double moneyReturned;

    private static final Logger logger = LoggerFactory.getLogger(VendingMachine.class);

    public VendingMachine() {
        this.selectState = new SelectionState(this);
        this.insertState = new InsertMoneyState(this);
        this.dispenseState = new DispenseState(this);
        this.cancelState = new CancelState(this);
        this.state = selectState;
        this.invList = new HashMap<Integer, VmInvModel>();
        this.moneyInserted = 0;
        this.moneyReturned = 0;
    }

    public SelectionState getSelectState() {
        return selectState;
    }

    public void setSelectState() {
        this.state = selectState;
    }

    public InsertMoneyState getInsertState() {
        return insertState;
    }

    public void setInsertState() {
        this.state = insertState;
    }

    public VendingMachineState getState() {
        return state;
    }

    public DispenseState getDispenseState() {
        return dispenseState;
    }

    public void setDispenseState() {
        this.state = dispenseState;
    }

    public CancelState getCancelState() {
        return cancelState;
    }

    public void setCancelState() {
        this.state = cancelState;
    }

    public HashMap<Integer, VmInvModel> showInventory(){
        List<VmInvModel> data = invHandler.listInventory();
        invList = null;
        int iter = 1;
        for( VmInvModel d : data){
            invList.put(iter++, d);
        }
        return invList;
    }

    public boolean isSelectionValid(long id){
        if(invList.containsKey(id)){
            selectedItem = invList.get(id);
            return true;
        }
        return false;
    }

    public boolean inStock(){
        return (selectedItem.getQuantity() > 1) ? true : false;
    }

    public double getRemainingPrice(){
        moneyReturned = selectedItem.getPrice() - moneyInserted;
        return moneyReturned;
    }

    public void resetSelection(){
        this.selectedItem = null;
        this.invList = null;
    }

    public void addMoney(int denomination){
        try {
            this.moneyInserted += denomination;
            this.moneyHandler.addMoneyFromUser(denomination);
        }
        catch(NoSuchDenominationException ex){
            logger.error(ex.getMessage());
        }
    }

    public boolean isMoneySufficient(int denomination){
         return (this.moneyInserted > this.selectedItem.getPrice() ) ? true : false;
    }

    // money entered - money inserted
    public boolean isReturnPossible(){
        this.moneyReturned = this.moneyInserted - selectedItem.getPrice();
        if(moneyReturned != 0)
            return moneyHandler.isChangePresent(moneyReturned);
        else
            return true;
    }

}
