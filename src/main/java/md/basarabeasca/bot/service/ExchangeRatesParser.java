package md.basarabeasca.bot.service;

import md.basarabeasca.bot.domain.Rate;

import java.util.List;

public interface ExchangeRatesParser {

    List<Rate> getExchangeRates();
}
