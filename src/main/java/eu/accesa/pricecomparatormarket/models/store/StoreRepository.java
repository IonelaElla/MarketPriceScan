package eu.accesa.pricecomparatormarket.models.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("FROM Store store WHERE store.name = :storeName")
    Optional<Store> findByName(String storeName);
}
