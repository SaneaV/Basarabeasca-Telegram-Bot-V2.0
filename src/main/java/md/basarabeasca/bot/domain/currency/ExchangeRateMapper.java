package md.basarabeasca.bot.domain.currency;

import md.basarabeasca.bot.infrastructure.jpa.ExchangeRateJpa;

public interface ExchangeRateMapper {

  ExchangeRate toEntity(ExchangeRateJpa exchangeRates);
}
