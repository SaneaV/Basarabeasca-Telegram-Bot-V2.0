package md.basarabeasca.bot.dao.mapper;

import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.dao.repository.model.ExchangeRateJpa;

public interface ExchangeRateMapper {

  ExchangeRate toEntity(ExchangeRateJpa exchangeRates);
}
