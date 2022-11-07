package com.application.vendingmachine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VM_INVENTORY")
public class VmInvModel {
    @Id
    @Column(name = "inventory_item", unique = true, nullable = false)
    private String item;
    @Column(name = "inventory_price", nullable = false)
    private double price;
    @Column(name = "inventory_quantity", nullable = false)
    private int quantity;

    public VmInvModel() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public VmInvModel(String item, double price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return ( "Item Name: " + this.item +
                "Item Price: " + this.price +
                "Item Quantity: " + this.quantity);
    }
}
