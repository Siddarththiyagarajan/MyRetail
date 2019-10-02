package myretail.service.impl;

import lombok.extern.slf4j.Slf4j;
import myretail.model.PriceDetails;
import myretail.model.ProductDetails;
import myretail.model.respose.InsertProductResponse;
import myretail.repository.ProductReactiveRepository;
import myretail.repository.ProductRepository;
import myretail.service.ItemService;
import myretail.service.PriceService;
import myretail.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private ItemService itemService;
    private PriceService priceService;
    private ProductRepository productRepository;
    private ProductReactiveRepository productReactiveRepository;
    
    @Autowired
    public ProductDetailsServiceImpl(ItemService itemService, PriceService priceService,
                                     ProductRepository productRepository,
                                     ProductReactiveRepository productReactiveRepository) {
        this.itemService = itemService;
        this.priceService = priceService;
        this.productRepository = productRepository;
        this.productReactiveRepository = productReactiveRepository;
    }

    @Override
    public Mono<ProductDetails> getProductDetails(String productId) {
        log.info("Before parallel call :: " + Thread.currentThread().getName());
        return Mono.zip(itemService.getItemDetails(productId), priceService.getPriceDetails(productId),
                (itemDetails, priceDetails) -> {
                    log.info("After parallel call :: " + Thread.currentThread().getName());
                    return mapResponse(itemDetails, priceDetails, productId);
                });
        /*return itemService.getItemDetails(productId)
                .zipWith(priceService.getPriceDetails(productId))
                .map(k -> mapResponse(k.getT1(), k.getT2(), productId));*/
    }

    @Override
    public Mono<String> insertProductDetails(ProductDetails productDetails) {
        log.info("ProductDetailsService :: insertProductDetails");
        productRepository.insertProduct(productDetails);
        return Mono.just("Success");
    }

    @Override
    public Mono<ProductDetails> getProductDetailsFromDB(String productId) {
        log.info("ProductDetailsService :: getProductDetailsFromDB");
        ProductDetails productDetailsFromDb = productRepository.getProduct(productId);
        return Mono.just(productDetailsFromDb);
    }

    @Override
    public Mono<InsertProductResponse> insertProductDetails(ProductDetails productDetails, Boolean isReactive) {
        log.info("ProductDetailsService :: insertProductDetails -> Thread : " + Thread.currentThread().getId());
        return productReactiveRepository
                .insert(productDetails.toProduct())
                .map(k -> {
                		return new InsertProductResponse(k.getProductId(), "Success"); 
                });
    }
    
    @Override
    public Flux<InsertProductResponse> insertProductDetails(Flux<ProductDetails> productDetailFlux) {
    		return productDetailFlux
    				.flatMap(productDetail -> insertProductDetails(productDetail, true))
    				.subscribeOn(Schedulers.parallel());
    }
    
    @Override
    public Mono<ProductDetails> getProductDetailsFromDB(String productId, Boolean isReactive) {
        log.info("ProductDetailsService :: getProductDetailsFromDB");
        return productReactiveRepository
                .findById(Integer.parseInt(productId))
                .flatMap(products ->
                        Mono.just(products.toProductDetails())
                );
    }

    private ProductDetails mapResponse(String productName, PriceDetails priceDetails, String productId) {
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(Integer.parseInt(productId));
        List<String> errorList = new ArrayList<>();

        if (!"ITEM_NOT_AVAILABLE".equals(productName)) {
            productDetails.setProductName(productName);
        } else {
            errorList.add("ITEM_NOT_AVAILABLE");
            productDetails.setContainsError(Boolean.TRUE);
        }

        if (!priceDetails.getContainsError()) {
            productDetails.setPriceDetails(priceDetails);
        } else {
            errorList.add(priceDetails.getPriceMsg().name());
            productDetails.setContainsError(Boolean.TRUE);
        }

        if (!errorList.isEmpty()) {
            productDetails.setErrorList(errorList);
        }
        return productDetails;
    }
}
