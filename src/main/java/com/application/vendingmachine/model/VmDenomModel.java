package com.application.vendingmachine.model;

import javax.persistence.*;

@Entity
@Table(name = "VM_Denomination")
public class VmDenomModel {
    @Id
    @Column(name = "denomination_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "denomination_value", nullable = false)
    private int value;

    public VmDenomModel() {
    }

    public VmDenomModel(int value) {
        this.value = value;
    }

    public VmDenomModel(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
