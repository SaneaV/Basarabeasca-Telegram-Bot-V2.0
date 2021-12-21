package md.basarabeasca.bot.feature.exchangerates.repository;

import md.basarabeasca.bot.feature.exchangerates.model.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRateModel, Long> {

    @Modifying
    @Query(value = "UPDATE exchange_rates SET value = ?2 WHERE currency = ?1", nativeQuery = true)
    void updateExchangeRate(String currency, String exchangeRate);
}
