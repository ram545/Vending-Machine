package com.application.vendingmachine.controller;

import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.service.VendingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class VmController {

    @Autowired
    private VendingMachine VmService;

    @GetMapping("vm")
    private HashMap<Integer, VmInvModel> showInventoryList(){
        return VmService.showInventory();
    }

    @PostMapping("vm/select")
    private String makeSelection(@RequestParam long id){
        return VmService.getState().selection(id);
    }
}
