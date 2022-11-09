package com.application.vendingmachine.controller;

import com.application.vendingmachine.exception.NoSuchDenominationException;
import com.application.vendingmachine.model.VmDenomModel;
import com.application.vendingmachine.model.VmInvModel;
import com.application.vendingmachine.model.VmMoneyModel;
import com.application.vendingmachine.service.VmMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VmMoneyController {

    @Autowired
    private VmMoneyService monService;

    private static final Logger logger = LoggerFactory.getLogger(VmMoneyController.class);

    @GetMapping("/denomination/show")
    public List<VmMoneyModel> getDenominationList(){
        return monService.getDenominationList();
    }

    @PostMapping("/denomination/add")
    public void addNewDenomination(@RequestParam int denomination, @RequestParam int quantity){
        try {
            monService.addDenominationEntry(denomination, quantity);
        }
        catch (NoSuchDenominationException ex){
            logger.error(ex.getMessage());
        }
    }

    @PostMapping("/denomination/delete/")
    public void deleteDenomination(@RequestParam int denomination){
        try {
            monService.deleteDenominationEntry(denomination);
        }
        catch (NoSuchDenominationException ex){
            logger.error(ex.getMessage());
        }
    }

    @PostMapping("/denomination/delete/all")
    public void deleteAll(){
        monService.deleteAll();
    }

    @GetMapping("/denomination/accepted/show")
    public List<VmDenomModel> showAcceptedDenominations(){
        return monService.showAcceptedDenominations();
    }

    @PostMapping("/denomination/accepted/add")
    public void addAcceptedDenominations(@RequestParam int[] data){
        monService.addAcceptedDenominations(data);
    }

    @PostMapping("/denomination/accepted/update")
    public void updateAcceptedDenominations(@RequestBody int[] data){
        monService.updateAcceptedDenominations(data);
    }

    @PostMapping("/denomination/accepted/delete")
    public void deleteAcceptedDenominations(){
        monService.deleteAcceptedDenominations();
    }

}
