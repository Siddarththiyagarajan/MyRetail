
LOCAL SetUp :-
----------------------------------------------------------------------------------------------------------------------
1. Pull cassandra docker image and run the container in port 9042
2. Run the cassandra.sh /scripts directory. This will create the necessary keyspace and tables in locally running cassandra
3. Get the Network IP of the machine from network preferences
4. Build

    Maven build

            mvn clean install -DcassandraHostIP=192.xxx.x.xxx

    Docker image build

            docker-compose up            

5. Health check

            http://<<ur_network_ip>>:8080/actuator/health
            Eg: http://192.168.104.225:8080/actuator/health

6. API

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

7. DOCKER REPOS:-

        hub.docker.com/siddarththiyagarajan/my_retail_app
        hub.docker.com/sid105/cassandra

        hub.docker.com/sid105/my_retail_repo

    Push a locally created image to the docker repo

   		docker tag myretailapp siddarththiyagarajan/my_retail_app
   		docker push siddarththiyagarajan/my_retail_app        

    USEFUL DOCKER COMMANDS :-

        docker images
        docker  rmi -f <image_id>
        docker ps
        docker ps -a

   	DOCKER_HUB URL :-
   	    https://hub.docker.com

8. Depricated Files and Process that is not used <br/>
  File :: docker-compose_2.yaml <br/>
  Change the volumes of MyRetailCassandra to your local folder in **docker-compose.yml** <br/>
  docker-compose up --build <br/>

            -> Creates a docker image of MyRetail app            
            -> Downloads the cassandra image, creates a container
            -> Runs the cassandra start up script to create initial keyspace and tables
            -> spawns an instance of myretailapp and connects to the cassandra container   	                              
----------------------------------------------------------------------------------------------------------------------
KUBERNETES DEPLOYMENT
----------------------------------------------------------------------------------------------------------------------
1. First setup the Cassandra cluster in your Kubernetes and run the startupscript. Refer to ReadMe.md in k8 directory.
2. Get the cassandra cluster ip

            kubectl get svc -n my-retail-stage

            NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)             AGE
            cassandra          ClusterIP   10.108.222.254   <none>        9042/TCP            42h
            cassandra-peers    ClusterIP   None             <none>        7000/TCP,7001/TCP   42h

    Cassandra Cluster Ip :: 10.108.222.254

3. In the application-dev.properties, change the 'cassandraHostIP' parameter to the cluster-ip of the cassandra.
     (OR)
    In the deploy.yaml file, change the 'cassandraHostIP' parameter in the APP_ARGS to the cluster-ip of the cassandra.
    In our case it is 10.108.222.254. This will make sure that our application will connect to the cassandra cluster
4. Deploy the application

            kubectl create -f deploy.yml

5. Access the application using the Nodeport

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
