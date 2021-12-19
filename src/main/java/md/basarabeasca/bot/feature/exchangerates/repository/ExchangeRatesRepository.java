package md.basarabeasca.bot.feature.exchangerates.repository;

import md.basarabeasca.bot.feature.exchangerates.model.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRateModel, Long> {

    @Query(value = "UPDATE exchange_rates e SET e.exchange_rate = ?2 WHERE currency = ?1", nativeQuery = true)
    void updateExchangeRate(String currency, String exchangeRate);
}
