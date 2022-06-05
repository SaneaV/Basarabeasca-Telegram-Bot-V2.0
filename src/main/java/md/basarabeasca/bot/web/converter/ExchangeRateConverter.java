package md.basarabeasca.bot.web.converter;

import java.util.List;
import md.basarabeasca.bot.domain.ExchangeRate;
import md.basarabeasca.bot.repository.model.ExchangeRateJpa;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;

public interface ExchangeRateConverter {

  String toString(List<ExchangeRateJpa> exchangeRateJpa);

  ExchangeRateDto toDto(ExchangeRate exchangeRate);
}