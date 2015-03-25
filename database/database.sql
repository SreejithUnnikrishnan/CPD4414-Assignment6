--create INVENTORY database
create database inventory;

use inventory;

-- create PRODUCTS table
create table products(
	product_id INTEGER(3) not null,
	name VARCHAR(30) not null,
	description VARCHAR(50) not null,
	quantity INTEGER(3) not null,
	CONSTRAINT products_product_id_pk PRIMARY KEY (product_id)
);



-- Insert data into PRODUCTS table
INSERT INTO products (product_id, name,description,quantity) VALUES (101,'lorem','lorem ipsum',4);
INSERT INTO products (product_id,name,description,quantity) VALUES (102,'dolor','dolor sit',12);


select * from products;

