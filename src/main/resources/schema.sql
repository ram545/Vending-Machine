DROP TABLE If EXISTS VM_INVENTORY;
CREATE TABLE VM_INVENTORY(
    inventory_item VARCHAR(50) NOT NULL,
    inventory_price DOUBLE PRECISION NOT NULL,
    inventory_quantity INT NOT NULL,
    PRIMARY KEY(inventory_item)
);

DROP TABLE IF EXISTS VM_MONETARY;
CREATE TABLE VM_MONETARY(
    monetary_denomination INT NOT NULL,
    monetary_quantity INT NOT NULL,
    PRIMARY KEY(monetary_denomination)
);

DROP TABLE IF EXISTS VM_DENOMINATION;
CREATE TABLE VM_DENOMINATION(
   denomination_id INT NOT NULL,
   denomination_value INT NOT NULL,
   PRIMARy KEY(denomination_id)
);