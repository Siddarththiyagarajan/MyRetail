package myretail.repository.impl;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import lombok.extern.slf4j.Slf4j;
import myretail.model.ProductDetails;
import myretail.repository.ProductRepository;
import myretail.repository.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private CassandraOperations cassandraOperations;

    @Autowired
    public ProductRepositoryImpl(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    @Override
    public void insertProduct(ProductDetails productDetails) {
        log.info("AddProductRepository :: insertProduct");
        cassandraOperations.insert(productDetails.toProduct());
    }

    @Override
    public ProductDetails getProduct(String productId) {
        log.info("AddProductRepository :: getProduct");
        Select.Where select = QueryBuilder.select().from("products")
                            .where(QueryBuilder.eq("product_id", Integer.parseInt(productId)));
        Products products = cassandraOperations.selectOne(select, Products.class);
        return products.toProductDetails();
    }
}
