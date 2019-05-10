package myretail.model;

import lombok.Getter;

public enum CurrencyCode {
    
    USD("USD"), INR("INR"), EUR("EUR");

    @Getter
    private String currencyCode;

    CurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
