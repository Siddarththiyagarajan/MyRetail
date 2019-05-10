package myretail.repository.domain;

import lombok.Getter;
import myretail.model.CurrencyCode;
import myretail.model.PriceDetails;
import myretail.model.ProductDetails;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Getter
public class Products {

    @PrimaryKey("product_id")
    private Integer productId;

    @Column("product_name")
    private String productName;

    @Column("price_value")
    private Double value;

    @Column("price_currency_code")
    private String currencyCode;

    public Products(Integer productId, String productName, Double value, String currencyCode) {
        this.productId = productId;
        this.productName = productName;
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public ProductDetails toProductDetails() {
        CurrencyCode currencyCode = CurrencyCode.valueOf(this.currencyCode);
        PriceDetails priceDetails = new PriceDetails(this.value, currencyCode);
        return new ProductDetails(this.productId, this.productName, priceDetails);
    }
}
