package myretail.router;

import myretail.handler.ProductDetailsApiHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
//@EnableWebFlux
public class ProductDetailsApi { //implements WebFluxConfigurer

    private ProductDetailsApiHandler productDetailsApiHandler;

    @Autowired
    public ProductDetailsApi(ProductDetailsApiHandler productDetailsApiHandler) {
        this.productDetailsApiHandler = productDetailsApiHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes() {
        return route(GET("/rest/products/{id}"), productDetailsApiHandler :: getProductDetails)
                .andRoute(POST("/rest/products/{id}"), productDetailsApiHandler :: createProductDetails)
                .andRoute(POST("/rest/createMultipleProducts"), productDetailsApiHandler :: createProducts);
    }

    /*@Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://allowed-origin.com")
                .allowedMethods("PUT")
                .maxAge(3600);
    }*/

}
