package eu.accesa.pricecomparatormarket.models.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("FROM Product product WHERE product.productId = :productId")
    Optional<Product> findByProductId(String productId);
}
