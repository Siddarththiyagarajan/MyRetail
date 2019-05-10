package myretail.service;

import reactor.core.publisher.Mono;

public interface ItemService {

    Mono<String> getItemDetails(String productId);
}
