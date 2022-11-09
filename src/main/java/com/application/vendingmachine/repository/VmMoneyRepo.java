package com.application.vendingmachine.repository;

import com.application.vendingmachine.model.VmMoneyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VmMoneyRepo extends JpaRepository<VmMoneyModel, Integer> {

}
