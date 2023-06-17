package md.basarabeasca.bot.infrastructure.service;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.fuel.Fuel;
import md.basarabeasca.bot.domain.fuel.FuelMapper;
import md.basarabeasca.bot.domain.fuel.FuelRepository;
import md.basarabeasca.bot.infrastructure.jpa.FuelJpa;
import md.basarabeasca.bot.infrastructure.parser.api.FuelParser;
import md.basarabeasca.bot.infrastructure.service.api.FuelService;
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
  public List<Fuel> getBestFuelPriceFor(String fuelType) {
    final List<Fuel> filteredFuelList = fuelParser.getAllFuelPriceList()
        .stream()
        .filter(f -> fuelType.equalsIgnoreCase(f.getType()) && nonNull(f.getPrice()))
        .collect(toList());

    if (isEmpty(filteredFuelList)) {
      return emptyList();
    }

    return filteredFuelList.stream()
        .collect(groupingBy(Fuel::getPrice, TreeMap::new, toList()))
        .firstEntry().getValue();
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
