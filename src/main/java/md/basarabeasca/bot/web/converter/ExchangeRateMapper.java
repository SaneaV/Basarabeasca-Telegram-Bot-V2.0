package md.basarabeasca.bot.web.converter;

import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;

public interface ExchangeRateMapper {

  ExchangeRateDto toDto(ExchangeRateJpa exchangeRateJpa);

  ExchangeRateDto toDto(ExchangeRate exchangeRate);
}