package md.basarabeasca.bot.feature.exchangerates.service.impl;

import lombok.Getter;
import md.basarabeasca.bot.feature.exchangerates.domain.ExchangeRate;
import md.basarabeasca.bot.feature.exchangerates.service.ExchangeRatesParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

        final Elements listRates = Objects.requireNonNull(document.getElementsByClass("view-rates")
                        .first())
                .select("li");

        final List<ExchangeRate> exchangeRates = new ArrayList<>();
        listRates.forEach(
                exchangeRate -> exchangeRates.add(new ExchangeRate(
                        exchangeRate.getElementsByClass("currency").text(),
                        exchangeRate.getElementsByClass("rate none").text()))
        );

        return exchangeRates;
    }
}
