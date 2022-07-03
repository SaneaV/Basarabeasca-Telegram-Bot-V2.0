package md.basarabeasca.bot.infrastructure.parser.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.BNM;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.EUR;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.FINCOMBANK;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MAIB;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.MOLDINDCONBANK;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.RON;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.RUB;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.UAH;
import static md.basarabeasca.bot.infrastructure.util.ExchangeRateUtil.USD;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import md.basarabeasca.bot.dao.domain.ExchangeRate;
import md.basarabeasca.bot.infrastructure.parser.ExchangeRateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExchangeRateParserImpl implements ExchangeRateParser {

  private static final String VIEW_RATES = "view-rates";
  private static final String CURRENCY = "currency";
  private static final String LI = "li";
  private static final String DEFAULT_EXCHANGE_RATE = "-";
  private static final String RATE_NONE = "rate none";
  private static final String RATE_UP = "rate up";
  private static final String RATE_DOWN = "rate down";
  private static final String TABLE_SORTER = "tablesorter";
  private static final String COLUMN_COLUMN_IND = "column-%s column-ind-%s";
  private static final String COLUMN_UAH_COLUMN_IND_HIDDEN = "column-UAH column-ind-hidden";

  private static final List<String> CURRENCY_INDEX = asList(USD, EUR, RUB, RON, UAH);

  private final String bnmSite;
  private final String cursMdSite;

  public ExchangeRateParserImpl(@Value("${site.exchange-rates.bnm}") String bnmSite,
      @Value("${site.best-exchange-rates.curs-md}") String cursMdSite) {
    this.bnmSite = bnmSite;
    this.cursMdSite = cursMdSite;
  }

  @Override
  public List<ExchangeRate> getBNMExchangeRates() {
    final Elements listRates = Objects.requireNonNull(
        getDocument(bnmSite).getElementsByClass(VIEW_RATES).first()).select(LI);

    return listRates.stream()
        .map(e -> {
          final String price = getValue(e);
          final String currency = e.getElementsByClass(CURRENCY).text();
          return new ExchangeRate(BNM, currency, price, price);
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<ExchangeRate> getAllExchangeRates() {
    return getPrivateBanksRates();
  }

  @Override
  public List<ExchangeRate> getPrivateBanksExchangeRates() {
    return getPrivateBanksRates();
  }

  private Document getDocument(String site) {
    try {
      return Jsoup.connect(site).get();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  private List<ExchangeRate> getPrivateBanksRates() {
    final Element table = getDocument(cursMdSite).getElementsByClass(TABLE_SORTER).get(0);
    return Stream.of(MOLDINDCONBANK, FINCOMBANK, MAIB)
        .map(bank -> getPrivateBankRates(bank, table))
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private List<ExchangeRate> getPrivateBankRates(String bank, Element table) {
    if (table.getElementsByClass(bank.toLowerCase()).size() == 0) {
      return emptyList();
    } else {
      final List<ExchangeRate> exchangeRates = new ArrayList<>();

      CURRENCY_INDEX.forEach(c -> {
        String findBy = String.format(COLUMN_COLUMN_IND, c, CURRENCY_INDEX.indexOf(c));
        if (c.equalsIgnoreCase(UAH)) {
          findBy = COLUMN_UAH_COLUMN_IND_HIDDEN;
        }
        final Elements prices = table.getElementsByClass(bank.toLowerCase()).get(0)
            .getElementsByClass(findBy);
        exchangeRates.add(new ExchangeRate(bank, c, getPrice(prices, 1), getPrice(prices, 0)));
      });

      return exchangeRates;
    }
  }

  private String getPrice(Elements prices, int index) {
    if (prices == null || prices.size() == 0 || prices.get(index) == null) {
      return DEFAULT_EXCHANGE_RATE;
    }
    return prices.get(index).text();
  }

  private String getValue(Element exchangeRate) {
    if (!exchangeRate.getElementsByClass(RATE_NONE).text().equalsIgnoreCase(EMPTY)) {
      return exchangeRate.getElementsByClass(RATE_NONE).text();
    } else if (!exchangeRate.getElementsByClass(RATE_UP).text().equalsIgnoreCase(EMPTY)) {
      return exchangeRate.getElementsByClass(RATE_UP).text();
    } else if (!exchangeRate.getElementsByClass(RATE_DOWN).text().equalsIgnoreCase(EMPTY)) {
      return exchangeRate.getElementsByClass(RATE_DOWN).text();
    }

    return DEFAULT_EXCHANGE_RATE;
  }
}
