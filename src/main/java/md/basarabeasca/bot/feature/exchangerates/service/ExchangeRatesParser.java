package md.basarabeasca.bot.feature.exchangerates.service;

import md.basarabeasca.bot.feature.Parser;
import md.basarabeasca.bot.feature.exchangerates.domain.ExchangeRate;

import java.util.List;

public interface ExchangeRatesParser extends Parser {

    List<ExchangeRate> getExchangeRates();
}
