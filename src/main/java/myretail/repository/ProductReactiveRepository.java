package myretail.repository;

import myretail.repository.domain.Products;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReactiveRepository extends ReactiveCassandraRepository<Products, Integer> {
}
