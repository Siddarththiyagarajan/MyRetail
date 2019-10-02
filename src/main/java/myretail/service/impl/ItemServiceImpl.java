package myretail.service.impl;

import lombok.extern.slf4j.Slf4j;
import myretail.config.PropertyConfig;
import myretail.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class ItemServiceImpl implements ItemService {

    private WebClient webClient;
    private PropertyConfig propertyConfig;

    @Autowired
    public ItemServiceImpl(WebClient webClient, PropertyConfig propertyConfig) {
        this.webClient = webClient;
        this.propertyConfig = propertyConfig;
    }

    @Override
    public Mono<String> getItemDetails(String productId) {

        log.info("Before making Item call. Thread :: " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return webClient.get().uri(propertyConfig.getItemUrl()
                .concat("/rest/item/itemDetails"), productId)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(String.class))
                .doOnSuccess(itemResponse -> log.info("Received item response for :: " + productId + " :: Thread :: " + Thread.currentThread().getName()))
                .onErrorResume(throwable -> {
                    log.error( "Error in receiving item response for  :: "
                            + productId + ". Error :: " + throwable.getMessage());
                    return Mono.just("ITEM_SERVICE_NOT_RESPONDING");
                })
                .subscribeOn(Schedulers.newElastic("ITEM"));
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
