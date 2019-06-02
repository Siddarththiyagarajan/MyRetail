job "my_retail" {
	datacenters = ["dc1"]
	type = "service"
	group "api" {
		count = 1
		
		task "my_retail_task" {
			driver = "docker"
			config {
				image = "sid105/my_retail_repo:latest"
				network_mode = "host"
				
				entrypoint = [
					"java",
					"-Xms1025m",
					"-Xmx1025m",
					"-Djava.security.egd=file:/dev/./urandom",
					"-jar",
					"/myRetail.jar"
				]
			}
			
			service {
				port = "http"
				name = "myretailapp"
				tags = [
					"live"
				]
				check {
					type = "http"
					port = "http"
					path = "/actuator/health"
					interval = "10s"
					timeout = "2s"
				}
			}
			
			resources {
			    network {
			      port "http" {}
			    }
  			}
		
		}
	}
}