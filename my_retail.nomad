job "my_retail" {
	datacenters = ["dc1"]
	type = "service"
	group "api" {
		count = 1
		
		task "my_retail_task" {
			driver = "docker"
			config {
				image = "siddarththiyagarajan/my_retail_app:latest"

				auth {
				    username = "siddarththiyagarajan"
				    password = "scarface105"
				}

				entrypoint = [
					"java",
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
