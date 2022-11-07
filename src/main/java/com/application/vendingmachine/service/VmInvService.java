package com.application.vendingmachine.service;

import com.application.vendingmachine.exception.NoSuchItemException;
import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.repository.VmInvRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VmInvService {
    @Autowired
    private VmInvRepo invHandle;

    private final static Logger logger = LoggerFactory.getLogger(VmInvService.class);
    // holds the data of the current selected item
    private VmInvModel transObj;

    public VmInvService() {
        this.transObj = null;
    }

    public void addNewItem(String item, Double price, int quantity){
        invHandle.save(new VmInvModel(item, price, quantity));
    }

    public void deleteItem(String item){
        try {
            invHandle.deleteById(item);
        }catch(EmptyResultDataAccessException ex){
            logger.error("No Item Found " + ex.getMessage());
        }
    }

    public List<VmInvModel> listItems(){
        ArrayList<VmInvModel> invData = new ArrayList<VmInvModel>();
        invHandle.findAll().forEach(invData::add);
        return invData;
    }

    public void loadItem(String item) throws NoSuchItemException {
        if(invHandle.existsById(item))
            transObj = (invHandle.findById(item)).get();
        else
            throw new NoSuchItemException("No Such Item exists in DataBase");
    }

    public boolean inStock(){
        return (transObj.getQuantity() > 0) ? true : false;
    }

    public double getPrice(){
        return transObj.getPrice();
    }

    public void updateItemData(){
        transObj.setPrice(transObj.getPrice()-1);
        invHandle.save(transObj);
        transObj = null;
    }
}
