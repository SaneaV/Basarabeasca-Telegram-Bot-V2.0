package md.basarabeasca.bot.web.mapper.impl;

import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import md.basarabeasca.bot.web.mapper.ExchangeRateMapper;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapperImpl implements ExchangeRateMapper {

  @Override
  public ExchangeRateDto toDto(ExchangeRateJpa exchangeRateJpa) {
    return ExchangeRateDto.builder()
        .purchase(exchangeRateJpa.getValue())
        .currency(exchangeRateJpa.getCurrency())
        .build();
  }

  @Override
  public ExchangeRateDto toDto(ExchangeRate exchangeRate) {
    return ExchangeRateDto.builder()
        .bankName(exchangeRate.getBankName())
        .currency(exchangeRate.getCurrency())
        .purchase(exchangeRate.getPurchase())
        .sale(exchangeRate.getSale())
        .build();
  }
}
