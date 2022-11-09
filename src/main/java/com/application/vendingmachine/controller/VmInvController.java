package com.application.vendingmachine.controller;

import com.application.vendingmachine.exception.NoSuchItemException;
import com.application.vendingmachine.exception.PriceMismatchException;
import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.service.VmInvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VmInvController {
    @Autowired
    private VmInvService invService;

    private final static Logger logger = LoggerFactory.getLogger(VmInvController.class);

    @GetMapping("/inventory/show")
    public List<VmInvModel> getInvData(){
        return invService.listInventory();
    }

    @PostMapping("/inventory/add")
    public void addItem(@RequestParam String item, @RequestParam double price, @RequestParam int quantity){
        try {
            invService.addItem(item,price,quantity);
        } catch (PriceMismatchException ex) {
            logger.error(ex.getMessage());
        }
    }

    @PutMapping("/inventory/update/price")
    public void updatePrice(@RequestParam String item, @RequestParam double price){
        try {
            invService.updatePrice(item,price);
        } catch (NoSuchItemException ex) {
            logger.error(ex.getMessage());
        }
    }

    @PutMapping("/inventory/update/quantity")
    public void updateQuantity(@RequestParam String item, @RequestParam int quantity){
        try {
            invService.updateQuantity(item,quantity);
        } catch (NoSuchItemException ex) {
            logger.error(ex.getMessage());
        }
    }
    @PostMapping("/inventory/update/all")
    public void updateAll(@RequestParam String item, @RequestParam double price, @RequestParam int quantity){
        try {
            invService.updateAll(item,price,quantity);
        } catch (NoSuchItemException ex) {
            logger.error(ex.getMessage());
        }
    }

    @PostMapping("/inventory/delete")
    public void deleteItem(@RequestParam String item){
        try {
            invService.deleteItem(item);
        } catch (NoSuchItemException ex) {
            logger.error(ex.getMessage());
        }
    }

    @PostMapping("/inventory/delete/all")
    public void deleteInventory(){
        invService.deleteInventory();
    }
}
