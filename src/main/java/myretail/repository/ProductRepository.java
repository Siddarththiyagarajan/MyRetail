package myretail.repository;

import myretail.model.ProductDetails;

public interface ProductRepository {

    void insertProduct(ProductDetails productDetails);
    ProductDetails getProduct(String productId);
}
