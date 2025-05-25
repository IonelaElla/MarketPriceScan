package eu.accesa.pricecomparatormarket.models.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("FROM Discount discount WHERE discount.store.id = :storeId AND discount.product.id = :productId AND discount.fromDate = :fromDate AND discount.toDate = :toDate")
    Optional<Discount> findBy(Long storeId, Long productId, LocalDate fromDate, LocalDate toDate);
}
