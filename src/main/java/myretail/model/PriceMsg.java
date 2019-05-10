package myretail.model;

import lombok.Getter;

public enum PriceMsg {

    PRICE_NOT_AVAILABLE("PRICE_NOT_AVAILABLE"), ERROR_CALLING_PRICE("ERROR_CALLING_PRICE");

    @Getter
    private String priceMsg;

    PriceMsg(String currencyCode) {
        this.priceMsg = currencyCode;
    }
}
