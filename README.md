
LOCAL Installation :-
----------------------------------------------------------------------------------------------------------------------

1. Cassandra script that needs to be run

    -> CREATE KEYSPACE IF NOT EXISTS myretail_keyspace WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };
    -> CREATE TABLE IF NOT EXISTS myretail_keyspace.products (product_id int PRIMARY KEY, product_name varchar, price_value decimal, price_currency_code varchar);

2. After downloading the project from GIT, please change cassandraHostIP in the property file to your machines network ip
3. Network ip can be obtained from the network preferences of the machine.
4. Build

            mvn clean install -DcassandraHostIP=192.xxx.x.xxx
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

9. After making any code
     
        Build the project using 

               mvn clean install
        
   	  Create a docker image using
   	
               docker-compose up —build
               
   	   Upload the image to the docker repo using the commands above
   	 
   	   Run the NOMAD file / K8, which will pull the latest image from the docker repo and will spin up the instances
  
10. DOCKER COMMANDS :-

        docker images
        docker  rmi -f <image_id>
        docker ps
        docker ps -a
        
    Push a locally created image to the docker repo
   		
   		docker tag myretailapp siddarththiyagarajan/my_retail_app
   		docker push siddarththiyagarajan/my_retail_app
   	
   	DOCKER_HUB URL :: https://hub.docker.com
   	USER
   	PASS                              


----------------------------------------------------------------------------------------------------------------------
SETTING UP THE APP IN KUBERNETES CLUSTER
----------------------------------------------------------------------------------------------------------------------
1. First setup the Cassandra cluster in your Kubernetes and run the startupscript. Refer to ReadMe.md in k8 directory.
2. Deploy the application

            kubectl create -f deploy.yml

3. Access the application using the Nodeport
 
            kubectl get svc -n my-retail-stage
            
            NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)             AGE
            cassandra          ClusterIP   10.108.222.254   <none>        9042/TCP            42h
            cassandra-peers    ClusterIP   None             <none>        7000/TCP,7001/TCP   42h
            myretail-service   NodePort    10.105.243.41    <none>        8080:31907/TCP      40h

    The service(myretail-service) is hosted as NodePort and can be accessed using the port 31907 like
    
            curl http://localhost:31907/actuator/health
                 
    

---------------------------------------------------------------------------------------------------------------------        
Tech Stack :-
---------------------------------------------------------------------------------------------------------------------

1. Spring Webflux
2. Spring Data Cassandra Reactive
3. Cassandra
4. Docker 
5. Kubernetes

---------------------------------------------------------------------------------------------------------------------
Spring Web Flux Guide
-----------------------------------------------------------------------------------------------------------
	1) https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/webflux-functional.adoc
	2) https://dzone.com/articles/creating-multiple-routerfunctions-in-spring-webflux
	3) https://www.baeldung.com/spring-5-functional-web
----------------------------------------------------------------------------------------------------------------------
NOMAD COMMANDS
----------------------------------------------------------------------------------------------------------------------
        Start the Client and Server in DEV mode 
        
        	sudo nomad agent -devzz
        
        
        NOMAD UI 
        
        	http://localhost:4646/ui/jobs
        
        
        Start the JOB:
        
        	nomad job run my_retail.nomad
        	-> Pulls the image from the docker repository and spins up the specified number of instances
        	
----------------------------------------------------------------------------------------------------------------------
KUBERNETES COMMANDS
----------------------------------------------------------------------------------------------------------------------        

        Get the Nodes
            kubectl get nodes
        
        Get the Pod
            kubectl get pods
        
        Create a Deployment
            kubectl create -f deploy.yml
        
        Delete a Deployment
            kubectl delete -f deploy.yml
        
        Get the status of Service
            kubectl get svc

----------------------------------------------------------------------------------------------------------------------
KUBERNETES UTILS
----------------------------------------------------------------------------------------------------------------------


        kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta8/aio/deploy/recommended.yaml

        kubectl proxy

To get Bearer token:

        kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')

Dashboard URL :

        http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/.

REFERENCE
        https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/

