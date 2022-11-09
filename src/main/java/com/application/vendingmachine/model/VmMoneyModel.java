package com.application.vendingmachine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VM_Monetary")
public class VmMoneyModel {
    @Id
    @Column(name = "monetary_denomination", nullable = false, unique = true)
    private int denomination;

    @Column(name = "monetary_quantity", nullable = false)
    private int quantity;

    public VmMoneyModel() {
    }

    public VmMoneyModel(int denomination, int quantity) {
        this.denomination = denomination;
        this.quantity = quantity;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "VmMoneyModel{" +
                "denomination=" + denomination +
                ", quantity=" + quantity +
                '}';
    }
}
