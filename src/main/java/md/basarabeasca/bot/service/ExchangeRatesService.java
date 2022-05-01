package md.basarabeasca.bot.service;

import md.basarabeasca.bot.web.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeRatesService {

    List<ExchangeRateDto> getBNMExchangeRates();

    List<ExchangeRateDto> getBestPrivateBankExchangeRateFor(String currency, String action);

    void updateExchangeRates();
}
