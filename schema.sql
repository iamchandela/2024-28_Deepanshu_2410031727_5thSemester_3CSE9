-- Walmart Pet Department Relational Schema

CREATE TABLE Manufacturer (
    manufacturer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Animal (
    animal_id INT PRIMARY KEY AUTO_INCREMENT,
    species_name VARCHAR(100) NOT NULL
);

CREATE TABLE Product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    manufacturer_id INT NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES Manufacturer(manufacturer_id)
);

CREATE TABLE Product_Animal (
    product_id INT NOT NULL,
    animal_id INT NOT NULL,
    PRIMARY KEY (product_id, animal_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (animal_id) REFERENCES Animal(animal_id)
);

CREATE TABLE PetFood (
    product_id INT PRIMARY KEY,
    weight DECIMAL(8,2) NOT NULL,
    flavor VARCHAR(100) NOT NULL,
    target_health_condition VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE PetToy (
    product_id INT PRIMARY KEY,
    material VARCHAR(100) NOT NULL,
    durability VARCHAR(50) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE PetApparel (
    product_id INT PRIMARY KEY,
    color VARCHAR(50) NOT NULL,
    size VARCHAR(20) NOT NULL,
    care_instructions TEXT,
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE Customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    transaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE TransactionItem (
    transaction_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    PRIMARY KEY (transaction_id, product_id),
    FOREIGN KEY (transaction_id) REFERENCES Transaction(transaction_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

CREATE TABLE Location (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL
);

CREATE TABLE Shipment (
    shipment_id INT PRIMARY KEY AUTO_INCREMENT,
    origin_location_id INT NOT NULL,
    destination_location_id INT NOT NULL,
    FOREIGN KEY (origin_location_id) REFERENCES Location(location_id),
    FOREIGN KEY (destination_location_id) REFERENCES Location(location_id)
);

CREATE TABLE ShipmentItem (
    shipment_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (shipment_id, product_id),
    FOREIGN KEY (shipment_id) REFERENCES Shipment(shipment_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);