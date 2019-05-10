package myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import myretail.repository.domain.Products;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ProductDetails {

    @JsonProperty("id")
    private Integer productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("current_price")
    private PriceDetails priceDetails;

    public ProductDetails(Integer productId, String productName, PriceDetails priceDetails) {
        this.productId = productId;
        this.productName = productName;
        this.priceDetails = priceDetails;
    }

    private Boolean containsError;

    @JsonProperty("error")
    private List<String> errorList;

    public Products toProduct() {
        return new Products(this.getProductId(), this.getProductName(),
                this.getPriceDetails().getValue(), this.getPriceDetails().getCurrencyCode().name());
    }
}
