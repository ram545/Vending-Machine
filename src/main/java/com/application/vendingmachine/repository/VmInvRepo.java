package com.application.vendingmachine.repository;

import com.application.vendingmachine.model.VmInvModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VmInvRepo extends JpaRepository<VmInvModel, String> {

}
