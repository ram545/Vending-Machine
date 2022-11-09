package com.application.vendingmachine.service;

import com.application.vendingmachine.exception.NoSuchItemException;
import com.application.vendingmachine.exception.PriceMismatchException;
import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.repository.VmInvRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addItem(String item, Double price, int quantity) throws PriceMismatchException {
        if(invHandle.existsById(item)){
            VmInvModel data = invHandle.findById(item).get();
            if(data.getPrice() != price)
                throw new PriceMismatchException("Please use update to make any prices changes to the existing inventory");
            quantity += data.getQuantity();
            invHandle.deleteById(item);
        }
        invHandle.save(new VmInvModel(item, price, quantity));
    }

    public void deleteItem(String item) throws NoSuchItemException {
        if(invHandle.existsById(item))
            invHandle.deleteById(item);
        else
            throw new NoSuchItemException("No Such Item exists in Inventory");
    }
    public void updatePrice(String item, double price) throws NoSuchItemException {
        if(invHandle.existsById(item)){
            VmInvModel data = invHandle.findById(item).get();
            deleteItem(item);
            data.setPrice(price);
            invHandle.save(data);
        }
        else
            throw new NoSuchItemException("No Such Item exists in Inventory");
    }
    public void updateQuantity(String item, int quantity) throws NoSuchItemException {
        if(invHandle.existsById(item)){
            VmInvModel data = invHandle.findById(item).get();
            deleteItem(item);
            data.setQuantity(quantity);
            invHandle.save(data);
        }
        else
            throw new NoSuchItemException("No Such Item exists in Inventory");
    }

    public void updateAll(String item, double price, int quantity) throws NoSuchItemException {
        if(invHandle.existsById(item)){
            deleteItem(item);
            invHandle.save(new VmInvModel(item,price,quantity));
        }
        else
            throw new NoSuchItemException("No Such Item exists in Inventory");
    }

    public void deleteInventory() {
        invHandle.deleteAll();
    }

    public List<VmInvModel> listInventory(){
        ArrayList<VmInvModel> invData = new ArrayList<VmInvModel>();
        invHandle.findAll().forEach(invData::add);
        return invData;
    }

    public void loadItem(String item) throws NoSuchItemException {
        if(invHandle.existsById(item))
            transObj = (invHandle.findById(item)).get();
        else
            throw new NoSuchItemException("No Such Item exists in Inventory");
    }

    public boolean inStock(){
        return (transObj.getQuantity() > 0) ? true : false;
    }

    public double getPrice(){
        return transObj.getPrice();
    }

    public void commitTransaction(){
        transObj.setPrice(transObj.getPrice()-1);
        invHandle.save(transObj);
        transObj = null;
    }

}
