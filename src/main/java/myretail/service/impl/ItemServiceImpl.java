package myretail.service.impl;

import lombok.extern.slf4j.Slf4j;
import myretail.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ItemServiceImpl implements ItemService {

    private WebClient webClient;

    @Autowired
    public ItemServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<String> getItemDetails(String productId) {

        /*webClient.get().uri("http://localhost:9090"
                .concat("/rest/item/itemDetails"), productId)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(String.class))
                .doOnSuccess(itemResponse -> log.info("Received item response for :: " + productId))
                .onErrorResume(throwable -> {
                    log.error( "Error in receiving item response for  :: "
                            + productId + ". Error :: " + throwable.getMessage());
                    return Mono.just("ITEM_SERVICE_NOT_RESPONDING");
                });*/

        return Mono.just("ITEM_NAME");  //return Mono.just("ITEM_NOT_AVAILABLE");
    }
    
    // TO HIT ITEM SERVICE FOR MULTIPLE ITEMS
    /*public Mono<String> insertProductDetails2(Flux<ProductDetails> productDetailFlux) {
	    	productDetailFlux
	    	  .flatMap(productDetail -> webClient.get().uri("/comments/{id}", productDetail)
	    	    .retrieve()
	    	    .bodyToMono(String.class))
	    	  .subscribeOn(Schedulers.parallel());
	
		return null;
    }*/
}
