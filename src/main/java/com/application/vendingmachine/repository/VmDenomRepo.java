package com.application.vendingmachine.repository;

import com.application.vendingmachine.model.VmDenomModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VmDenomRepo extends JpaRepository<VmDenomModel, Integer> {
}
