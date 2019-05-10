package myretail.service.impl;

import lombok.extern.slf4j.Slf4j;
import myretail.model.CurrencyCode;
import myretail.model.PriceDetails;
import myretail.model.PriceMsg;
import myretail.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PriceServiceImpl implements PriceService {

    private WebClient webClient;

    @Autowired
    public PriceServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<PriceDetails> getPriceDetails(String productId) {

        /*webClient.get().uri("http://localhost:7070"
                .concat("/rest/price/priceDetails"), productId)
                .exchange()
                .flatMap(priceResponse -> priceResponse.bodyToMono(PriceDetails.class))
                .doOnSuccess(priceResponse -> log.info("Received price response for :: " + productId))
                .onErrorResume(throwable -> {
                    log.error( "Error in receiving price response for  :: "
                            + productId + ". Error :: " + throwable.getMessage());
                    PriceDetails priceDetails = new PriceDetails();
                    priceDetails.setPriceMsg(PriceMsg.ERROR_CALLING_PRICE);
                    priceDetails.setContainsError(Boolean.TRUE);
                    return Mono.just(priceDetails);
                });*/

        PriceDetails priceDetails = new PriceDetails();
        priceDetails.setValue(200.67);
        priceDetails.setCurrencyCode(CurrencyCode.EUR);
        priceDetails.setContainsError(Boolean.FALSE);
        //priceDetails.setPriceMsg(PriceMsg.PRICE_NOT_AVAILABLE);
        return Mono.just(priceDetails);
    }
}
