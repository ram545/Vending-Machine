package com.application.vendingmachine.service;

import com.application.vendingmachine.exception.NoSuchDenominationException;
import com.application.vendingmachine.model.VmDenomModel;
import com.application.vendingmachine.model.VmMoneyModel;
import com.application.vendingmachine.repository.VmDenomRepo;
import com.application.vendingmachine.repository.VmMoneyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VmMoneyService {
    @Autowired
    private VmMoneyRepo monHandle;

    @Autowired
    // used to handle list of accepted denominations
    private VmDenomRepo denomHandle;

    private HashMap<Integer, Integer> changePresent;
    private HashMap<Integer, Integer> changeAdded;
    private HashMap<Integer, Integer> changeToReturn;

    private static final Logger logger = LoggerFactory.getLogger(VmMoneyService.class);
    List<Integer> acceptedDenominations;

    public VmMoneyService() {
        this.changePresent = null;
        this.changeAdded = null;
        this.changeToReturn = null;
        Integer[] denominations = new Integer[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
        this.acceptedDenominations = Arrays.asList(denominations);
    }
    public List<VmMoneyModel> getDenominationList(){
        ArrayList<VmMoneyModel> denomList = new ArrayList<VmMoneyModel>();
        monHandle.findAll().forEach(denomList::add);
        return denomList;
    }

    public void addDenominationEntry(int denomination, int quantity) throws NoSuchDenominationException {
        if(acceptedDenominations.contains(denomination)){
            if(monHandle.existsById(denomination)){
                VmMoneyModel data = monHandle.findById(denomination).get();
                quantity += data.getQuantity();
            }
            monHandle.save(new VmMoneyModel(denomination,quantity));
        }
        else
            throw new NoSuchDenominationException("Please check the denomination you have entered");
    }

    public void deleteDenominationEntry(int denomination) throws NoSuchDenominationException {
        if(monHandle.existsById(denomination))
            monHandle.deleteById(denomination);
        else
            throw new NoSuchDenominationException("Please check the denomination you have entered");
    }

    public void deleteAll() {
        monHandle.deleteAll();
    }

    public void updateDenominationDb(){
        changePresent.forEach((K,V) -> {
            if(monHandle.existsById(K))
                monHandle.deleteById(K);
            monHandle.save(new VmMoneyModel(K,V));
        });
    }

    public List<VmDenomModel> showAcceptedDenominations(){
        return denomHandle.findAll();
    }

    public void addAcceptedDenominations(int[] arr){
        for(int a:arr)
            denomHandle.save(new VmDenomModel(a));
    }

    public void updateAcceptedDenominations(int[] arr){
        denomHandle.deleteAll();
        addAcceptedDenominations(arr);
    }

    public void deleteAcceptedDenominations(){
        denomHandle.deleteAll();
    }

    public void loadMoney(){
        List<VmMoneyModel> data = getDenominationList();
        data.forEach(a -> {changePresent.put(a.getDenomination(),a.getQuantity());});
    }

    public void loadAcceptedDenominationsList(){
        List<VmDenomModel> data = showAcceptedDenominations();
        data.forEach(a -> { acceptedDenominations.add(a.getValue()); });
    }

    // checks whether the required change is present
    public boolean isChangePresent(double amount){
        boolean isPresent = false;
        int remainder = 0, denomination = 0, quotient = 0, quantity = 0;
        ListIterator<Integer> iter = acceptedDenominations.listIterator(acceptedDenominations.size());
        while(amount > 0 && iter.hasPrevious()){
            if(!changePresent.containsKey(denomination))
                continue;
            denomination = iter.previous();
            quantity = changePresent.get(denomination);
            if(denomination <= amount){
                quotient = (int) (amount/denomination);
                quotient = (quotient < quantity) ? quotient : quantity;
                amount -= (quotient*denomination);
                changeToReturn.put(denomination,quotient);
                changePresent.put(denomination, quantity-quotient);
            }
        }
        if(amount == 0)
            isPresent = true;
        return isPresent;
    }

    // Used to add total money dispensed by user
    public void addMoneyFromUser(int denomination) throws NoSuchDenominationException {
        int quantity = 1;
        if(acceptedDenominations.contains(denomination)){
            if(changeAdded.containsKey(denomination))
                quantity = changeAdded.get(denomination) + 1;
            changeAdded.put(denomination,quantity);
            if(changePresent.containsKey(denomination))
                quantity = changePresent.get(denomination) + 1;
            changePresent.put(denomination,quantity);
        }
        else
            throw new NoSuchDenominationException("Please check the denomination you have entered");
    }

    // Dispense return change after successful transaction and commit
    public void dispenseChangeAndCommit(){
        changeToReturn.forEach( (K,V) -> {logger.debug("Denomination: " + K + "Number of Notes: " + V);});
        changeToReturn = null;
        changePresent = null;
        updateDenominationDb();
    }

    // Dispense the amount provided by user on cancel
    public void dispenseOnCancel(){
        int denomination = 0, quantity = 0;
        changePresent.forEach( (K,V) -> {logger.debug("Denomination: " + K + "Number of Notes: " + V);});
        Iterator<Integer> iter = acceptedDenominations.iterator();
        while(iter.hasNext()){
            quantity = 0;
            denomination = iter.next();
            if(changeAdded.containsKey(denomination))
                quantity -= changeAdded.get(denomination);
            if(changeToReturn.containsKey(denomination))
                quantity += changeToReturn.get(denomination);
            changePresent.put(denomination, changePresent.get(denomination)+quantity);
        }
        changePresent = null;
        changeToReturn = null;
    }
}
