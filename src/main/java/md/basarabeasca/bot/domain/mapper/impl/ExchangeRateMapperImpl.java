package md.basarabeasca.bot.domain.mapper.impl;

import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.domain.mapper.ExchangeRateMapper;
import md.basarabeasca.bot.domain.repository.model.ExchangeRateJpa;
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
