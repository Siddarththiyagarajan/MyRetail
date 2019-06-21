package myretail.service.impl;

import lombok.extern.slf4j.Slf4j;
import myretail.model.CurrencyCode;
import myretail.model.PriceDetails;
import myretail.model.PriceMsg;
import myretail.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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

        log.info("Before making Price call. Thread :: " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return webClient.get().uri("http://www.mocky.io/v2/5d0a44d22f00004d00e3eaf1")
                //.concat("/rest/price/priceDetails"), productId)
                .exchange()
                .flatMap(priceResponse -> priceResponse.bodyToMono(PriceDetails.class))
                .doOnSuccess(priceResponse -> log.info("Received price response for :: " + productId + " :: Thread :: " + Thread.currentThread().getName()))
                .onErrorResume(throwable -> {
                    log.error( "Error in receiving price response for  :: "
                            + productId + ". Error :: " + throwable.getMessage());
                    PriceDetails priceDetails = new PriceDetails();
                    priceDetails.setPriceMsg(PriceMsg.ERROR_CALLING_PRICE);
                    priceDetails.setContainsError(Boolean.TRUE);
                    return Mono.just(priceDetails);
                })
                .subscribeOn(Schedulers.newElastic("PRICE"));

        /*log.info("PriceService Thread :: " + Thread.currentThread().getId());
        PriceDetails priceDetails = new PriceDetails();
        priceDetails.setValue(200.67);
        priceDetails.setCurrencyCode(CurrencyCode.EUR);
        priceDetails.setContainsError(Boolean.FALSE);
        //priceDetails.setPriceMsg(PriceMsg.PRICE_NOT_AVAILABLE);
        return Mono.just(priceDetails).subscribeOn(Schedulers.parallel());*/
    }
}
