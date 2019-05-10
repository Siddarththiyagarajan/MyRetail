package myretail.service;

import myretail.model.PriceDetails;
import reactor.core.publisher.Mono;

public interface PriceService {

    Mono<PriceDetails> getPriceDetails(String productId);
}
