package myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class PriceDetails {

    @JsonProperty("value")
    private Double value;

    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;

    public PriceDetails(Double value, CurrencyCode currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    private Boolean containsError;
    private PriceMsg priceMsg;

}
