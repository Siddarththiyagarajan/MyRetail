CREATE KEYSPACE IF NOT EXISTS myretail_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS myretail_keyspace.products (
	product_id int PRIMARY KEY, 
	product_name varchar, 
	price_value decimal, 
	price_currency_code varchar);