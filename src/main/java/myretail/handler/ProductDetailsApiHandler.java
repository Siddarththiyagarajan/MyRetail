package myretail.handler;

import myretail.model.ProductDetails;
import myretail.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Slf4j
@Component
public class ProductDetailsApiHandler {

    private ProductDetailsService productDetailsService;

    @Autowired
    public ProductDetailsApiHandler(ProductDetailsService productDetailsService) {
        this.productDetailsService = productDetailsService;
    }

    public Mono<ServerResponse> getProductDetails(ServerRequest request) {
        log.info("ProductDetailsApiHandler :: getProductDetails");
        String productId = request.pathVariable("id");
        Optional<String> fromDb = request.queryParam("fromDb");
        Mono<ProductDetails> productDetailsMono = null;
        if (fromDb.isPresent() && "true".equals(fromDb.get())) {
            productDetailsMono = productDetailsService.getProductDetailsFromDB(productId, true);
        } else {
            productDetailsMono = productDetailsService.getProductDetails(productId);
        }

        if (null == productDetailsMono) {
            return ServerResponse.notFound().build();
        }
        return productDetailsMono.flatMap(k -> ServerResponse.ok().body(fromObject(k)));
    }

    public Mono<ServerResponse> createProductDetails(ServerRequest request) {
        log.info("ProductDetailsApiHandler :: getProductDetails");
        return request.bodyToMono(ProductDetails.class)
                .flatMap(productDetails -> productDetailsService.insertProductDetails(productDetails, true))
                .flatMap(k -> ServerResponse.ok().body(fromObject(k)));
    }

    public Mono<ServerResponse> createProducts(ServerRequest request) {
        log.info("ProductDetailsApiHandler :: createProducts");
        return request.bodyToFlux(ProductDetails.class)
                .flatMap(productDetails -> productDetailsService.insertProductDetails(productDetails, true))
                .collectSortedList()
                .flatMap(k -> ServerResponse.ok().body(fromObject(k)));
    }

}
