package com.application.vendingmachine.controller;

import com.application.vendingmachine.exception.NoSuchItemException;
import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.service.VmInvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VmInvController {
    @Autowired
    private VmInvService invService;

    private final static Logger logger = LoggerFactory.getLogger(VmInvController.class);

    @GetMapping("/ShowInventory")
    public List<VmInvModel> getInvData(){
        return invService.listItems();
    }

    @PostMapping("/addItem")
    public void addNewItem(@RequestParam String item, @RequestParam double price, @RequestParam int quantity){
        invService.addNewItem(item,price,quantity);
    }

    @PostMapping("/deleteItem")
    public void deleteItem(@RequestParam String item){
        invService.deleteItem(item);
    }
}
