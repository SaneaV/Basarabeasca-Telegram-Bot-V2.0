package md.basarabeasca.bot.feature.exchangerates.service.impl;

import lombok.Getter;
import md.basarabeasca.bot.feature.exchangerates.domain.ExchangeRate;
import md.basarabeasca.bot.feature.exchangerates.service.ExchangeRatesParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Component
public class ExchangeRatesParserImpl implements ExchangeRatesParser {

    private static final String VIEW_RATES = "view-rates";
    private static final String CURRENCY = "currency";
    private static final String LI = "li";
    private static final String DEFAULT_EXCHANGE_RATE = "-";
    private static final String RATE_NONE = "rate none";
    private static final String RATE_UP = "rate up";
    private static final String RATE_DOWN = "rate down";
    private static final String EMPTY_RATE = "";

    private final String site;

    public ExchangeRatesParserImpl(
            @Value("${site.exchange-rates.bnm}") String site) {
        this.site = site;
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return parseToExchangeRates();
    }

    private Document getDocument() throws IOException {
        return Jsoup.connect(site).get();
    }

    private List<ExchangeRate> parseToExchangeRates() {
        Document document = null;
        try {
            document = getDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert document != null;

        final Elements listRates = Objects.requireNonNull(document.getElementsByClass(VIEW_RATES)
                        .first())
                .select(LI);

        final List<ExchangeRate> exchangeRates = new ArrayList<>();
        listRates.forEach(
                exchangeRate ->
                        exchangeRates.add(new ExchangeRate(
                                exchangeRate.getElementsByClass(CURRENCY).text(),
                                getValue(exchangeRate)))
        );

        return exchangeRates;
    }

    private String getValue(Element exchangeRate) {
        if (!exchangeRate.getElementsByClass(RATE_NONE).text().equalsIgnoreCase(EMPTY_RATE)) {
            return exchangeRate.getElementsByClass(RATE_NONE).text();
        } else if (!exchangeRate.getElementsByClass(RATE_UP).text().equalsIgnoreCase(EMPTY_RATE)) {
            return exchangeRate.getElementsByClass(RATE_UP).text();
        } else if (!exchangeRate.getElementsByClass(RATE_DOWN).text().equalsIgnoreCase(EMPTY_RATE)) {
            return exchangeRate.getElementsByClass(RATE_DOWN).text();
        }

        return DEFAULT_EXCHANGE_RATE;
    }
}
