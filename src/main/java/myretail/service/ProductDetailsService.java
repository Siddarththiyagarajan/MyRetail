package myretail.service;

import myretail.model.ProductDetails;
import myretail.model.respose.InsertProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductDetailsService {

    Mono<ProductDetails> getProductDetails(String productId);
    Mono<String> insertProductDetails(ProductDetails productDetails);
    Mono<ProductDetails> getProductDetailsFromDB(String productId);
    Mono<InsertProductResponse> insertProductDetails(ProductDetails productDetails, Boolean isReactive);
    Mono<ProductDetails> getProductDetailsFromDB(String productId, Boolean isReactive);
    Flux<InsertProductResponse> insertProductDetails(Flux<ProductDetails> productDetailFlux);
}
