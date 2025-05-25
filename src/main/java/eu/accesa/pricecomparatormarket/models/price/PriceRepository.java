package eu.accesa.pricecomparatormarket.models.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("FROM Price price WHERE price.store.id = :storeId AND price.product.id = :productId AND price.effectiveDate = :effectiveDate")
    Optional<Price> findBy(Long storeId, Long productId, LocalDate effectiveDate);

    @Query("FROM Price price WHERE (:storeName IS NULL OR price.store.name = :storeName) AND (:productCategory IS NULL OR price.product.productCategory = :productCategory) AND (:productBrand IS NULL OR price.product.brand = :productBrand)")
    List<Price> filterBy(@Param("storeName") String storeName, @Param("productCategory") String productCategory, @Param("productBrand") String productBrand);



}
