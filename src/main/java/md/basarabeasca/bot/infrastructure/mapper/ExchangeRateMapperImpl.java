package md.basarabeasca.bot.infrastructure.mapper;

import md.basarabeasca.bot.domain.currency.ExchangeRate;
import md.basarabeasca.bot.domain.currency.ExchangeRateMapper;
import md.basarabeasca.bot.infrastructure.jpa.ExchangeRateJpa;
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
