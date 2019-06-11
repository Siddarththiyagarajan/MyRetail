Installation :-

1. Cassandra script that needs to be run

    -> CREATE KEYSPACE IF NOT EXISTS myretail_keyspace WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };
    -> CREATE TABLE IF NOT EXISTS myretail_keyspace.products (product_id int PRIMARY KEY, product_name varchar, price_value decimal, price_currency_code varchar);

2. After downloading the project from GIT, please change cassandraHostIP in the property file to your machines network ip
3. Network ip can be obtained from the network preferences of the machine.
4. mvn clean install
5. Change the volumes of MyRetailCassandra to your local folder in **docker-compose.yml**
6. docker-compose up --build
 
            -> Creates a docker image of MyRetail app            
            -> Downloads the cassandra image, creates a container
            -> Runs the cassandra start up script to create initial keyspace and tables
            -> spawns an instance of myretailapp and connects to the cassandra container
            
7. Health check

            http://<<ur_network_ip>>:8080/actuator/health
            Eg: http://192.168.104.225:8080/actuator/health

8. API

        GET -> http://<<ur_network_ip>>:8080/rest/products/{id}
        GET -> http://<<ur_network_ip>>:8080/rest/products/{id}?fromDb=true
        POST -> http://<<ur_network_ip>>:8080/rest/products/{id}
        
        Sample Request Body :-        
        {
            "id": 2134255,
            "name": "ItemName",
            "current_price": {
                "value": 300.67,
                "currency_code": "USD"
            }
        }                     
        
Tech Stack :-

1. Spring Webflux
2. Spring Data Cassandra Reactive
3. Cassandra
4. Docker 

---------------------------------------------------------------------------------------------------------------------
Spring Web Flux Guide
-----------------------------------------------------------------------------------------------------------
	1) https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/webflux-functional.adoc
	2) https://dzone.com/articles/creating-multiple-routerfunctions-in-spring-webflux
	3) https://www.baeldung.com/spring-5-functional-web

----------------------------------------------------------------------------------------------------------------------
        