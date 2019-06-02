#!/bin/bash

echo "Inside script"

CREATE_KEYSPACE = "CREATE KEYSPACE IF NOT EXISTS myretail_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };"
CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS myretail_keyspace.products (
	product_id int PRIMARY KEY, 
	product_name varchar, 
	price_value decimal, 
	price_currency_code varchar);"

HOSTPARAMS=`ping -c1 MyRetailCassandra | sed -nE 's/^PING[^(]+\(([^)]+)\).*/\1/p'`

# Wait for MyRetailCassandra to start up with a timeout of 120 seconds
i=0
while ! cqlsh $HOSTPARAMS -e "describe cluster" && [ ${i} -lt 120 ] ; do
	sleep 1
	i=$(( ${i} + 1 ))
done

echo "cqlsh $HOSTPARAMS -e $CREATE_KEYSPACE" 
cqlsh $HOSTPARAMS -e "$CREATE_KEYSPACE"
cqlsh $HOSTPARAMS -e "$CREATE_PRODUCTS_TABLE"  
