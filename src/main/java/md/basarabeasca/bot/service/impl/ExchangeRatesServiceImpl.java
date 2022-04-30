package md.basarabeasca.bot.service.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.Rate;
import md.basarabeasca.bot.web.dto.ExchangeRateDto;
import md.basarabeasca.bot.repository.model.ExchangeRate;
import md.basarabeasca.bot.repository.ExchangeRatesRepository;
import md.basarabeasca.bot.service.ExchangeRatesParser;
import md.basarabeasca.bot.service.ExchangeRatesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRatesParser exchangeRatesParser;
    private final ModelMapper modelMapper;

    @Override
    public List<ExchangeRateDto> getExchangeRates() {
        final List<ExchangeRate> exchangeRates = exchangeRatesRepository.findAll();

        if (isEmpty(exchangeRates)) {
            return saveExchangeRates().stream()
                    .map(this::convertToDTO)
                    .collect(toList());
        }

        return exchangeRates.stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public void updateExchangeRates() {
        final List<Rate> rates = exchangeRatesParser.getExchangeRates();

        rates.forEach(ex -> exchangeRatesRepository.updateExchangeRate(ex.getCurrency(), ex.getValue()));
    }

    private List<ExchangeRate> saveExchangeRates() {
        final List<Rate> rates = exchangeRatesParser.getExchangeRates();

        final List<ExchangeRate> exchangeRates = rates.stream()
                .map(rate -> exchangeRatesRepository.save(
                        ExchangeRate.builder()
                                .currency(rate.getCurrency())
                                .value(rate.getValue())
                                .build()))
                .collect(toList());

        return exchangeRatesRepository.saveAll(exchangeRates);
    }

    public ExchangeRateDto convertToDTO(ExchangeRate exchangeRate) {
        return modelMapper.map(exchangeRate, ExchangeRateDto.class);
    }
}
