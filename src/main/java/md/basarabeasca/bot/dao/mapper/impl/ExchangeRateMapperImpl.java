package md.basarabeasca.bot.dao.mapper.impl;

import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.dao.mapper.ExchangeRateMapper;
import md.basarabeasca.bot.dao.repository.model.ExchangeRateJpa;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapperImpl implements ExchangeRateMapper {

  @Override
  public ExchangeRate toEntity(ExchangeRateJpa exchangeRate) {
    return ExchangeRate.builder()
        .currency(exchangeRate.getCurrency())
        .purchase(exchangeRate.getValue())
        .build();
  }
}
