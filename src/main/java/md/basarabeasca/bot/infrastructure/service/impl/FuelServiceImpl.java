package md.basarabeasca.bot.infrastructure.service.impl;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.dao.mapper.FuelMapper;
import md.basarabeasca.bot.dao.repository.FuelRepository;
import md.basarabeasca.bot.dao.repository.model.FuelJpa;
import md.basarabeasca.bot.infrastructure.parser.FuelParser;
import md.basarabeasca.bot.infrastructure.service.FuelService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelServiceImpl implements FuelService {

  private final FuelParser fuelParser;
  private final FuelRepository fuelRepository;
  private final FuelMapper fuelMapper;

  @Override
  public List<Fuel> getANREFuelPrice() {
    return getANREFuelPriceJpas().stream()
        .map(fuelMapper::toEntity)
        .collect(toList());
  }

  @Override
  public List<Fuel> getAllFuelPriceList() {
    return fuelParser.getAllFuelPriceList();
  }

  @Override
  public void updateANREFuelPrice() {
    final List<Fuel> fuelList = fuelParser.getANREFuelPrice();
    fuelList.forEach(f -> fuelRepository.updateANREFuelPrice(f.getType(), f.getPrice()));
  }

  private List<FuelJpa> getANREFuelPriceJpas() {
    final List<FuelJpa> fuelJpas = fuelRepository.findAll();

    if (isEmpty(fuelJpas)) {
      return saveFuelPrices();
    }

    return fuelJpas;
  }

  private List<FuelJpa> saveFuelPrices() {
    try {
      final List<Fuel> fuelPrices = fuelParser.getANREFuelPrice();
      final List<FuelJpa> fuelPricesJpas = fuelPrices.stream()
          .map(fuel -> FuelJpa.builder()
              .type(fuel.getType())
              .price(fuel.getPrice())
              .build())
          .collect(toList());

      return fuelRepository.saveAll(fuelPricesJpas);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
