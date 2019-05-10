package myretail.service;

import myretail.model.ProductDetails;
import reactor.core.publisher.Mono;

public interface ProductDetailsService {

    Mono<ProductDetails> getProductDetails(String productId);
    Mono<String> insertProductDetails(ProductDetails productDetails);
    Mono<ProductDetails> getProductDetailsFromDB(String productId);
    Mono<String> insertProductDetails(ProductDetails productDetails, Boolean isReactive);
    Mono<ProductDetails> getProductDetailsFromDB(String productId, Boolean isReactive);
}
