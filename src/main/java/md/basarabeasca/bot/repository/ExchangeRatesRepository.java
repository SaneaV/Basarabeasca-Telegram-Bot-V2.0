package md.basarabeasca.bot.repository;

import md.basarabeasca.bot.repository.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRate, Long> {

    @Modifying
    @Query(value = "UPDATE exchange_rates SET value = ?2 WHERE currency = ?1", nativeQuery = true)
    void updateExchangeRate(String currency, String exchangeRate);
}
