package md.basarabeasca.bot.feature.exchangerates.service.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.feature.exchangerates.domain.ExchangeRate;
import md.basarabeasca.bot.feature.exchangerates.dto.ExchangeRateDto;
import md.basarabeasca.bot.feature.exchangerates.model.ExchangeRateModel;
import md.basarabeasca.bot.feature.exchangerates.repository.ExchangeRatesRepository;
import md.basarabeasca.bot.feature.exchangerates.service.ExchangeRatesParser;
import md.basarabeasca.bot.feature.exchangerates.service.ExchangeRatesService;
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
        final List<ExchangeRateModel> exchangeRateModels = exchangeRatesRepository.findAll();

        if (isEmpty(exchangeRateModels)) {
            return saveExchangeRates().stream()
                    .map(this::convertToDTO)
                    .collect(toList());
        }

        return exchangeRateModels.stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public void updateExchangeRates() {
        final List<ExchangeRate> exchangeRates = exchangeRatesParser.getExchangeRates();

        exchangeRates.forEach(ex -> exchangeRatesRepository.updateExchangeRate(ex.getCurrency(), ex.getValue()));
    }

    private List<ExchangeRateModel> saveExchangeRates() {
        final List<ExchangeRate> exchangeRates = exchangeRatesParser.getExchangeRates();

        final List<ExchangeRateModel> exchangeRateModels = exchangeRates.stream()
                .map(exchangeRate -> exchangeRatesRepository.save(
                        ExchangeRateModel.builder()
                                .currency(exchangeRate.getCurrency())
                                .value(exchangeRate.getValue())
                                .build()))
                .collect(toList());

        return exchangeRatesRepository.saveAll(exchangeRateModels);
    }

    public ExchangeRateDto convertToDTO(ExchangeRateModel exchangeRateModel) {
        return modelMapper.map(exchangeRateModel, ExchangeRateDto.class);
    }
}
