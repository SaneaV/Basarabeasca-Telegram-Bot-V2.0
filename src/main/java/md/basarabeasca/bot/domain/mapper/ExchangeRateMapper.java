package md.basarabeasca.bot.domain.mapper;

import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;

public interface ExchangeRateMapper {

  ExchangeRate toEntity(ExchangeRateJpa exchangeRates);
}
