package md.basarabeasca.bot.feature.exchangerates.service;

import md.basarabeasca.bot.feature.exchangerates.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeRatesService {

    List<ExchangeRateDto> getExchangeRates();

    void updateExchangeRates();
}
