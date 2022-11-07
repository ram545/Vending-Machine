DROP TABLE If EXISTS VM_INVENTORY;
CREATE TABLE VM_INVENTORY(
    inventory_item VARCHAR(50) NOT NULL,
    inventory_price DOUBLE PRECISION NOT NULL,
    inventory_quantity INT NOT NULL,
    PRIMARY KEY(inventory_item)
);