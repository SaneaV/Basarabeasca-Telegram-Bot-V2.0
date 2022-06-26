package md.basarabeasca.bot.infrastructure.facade.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.infrastructure.converter.FuelConverter;
import md.basarabeasca.bot.infrastructure.facade.FuelFacade;
import md.basarabeasca.bot.infrastructure.service.FuelService;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelFacadeImpl implements FuelFacade {

  private final FuelService fuelService;
  private final FuelConverter fuelConverter;
  private final UpdateDateService updateDateService;

  @Override
  public String getANREFuelPrice() {
    updateDateService.checkUpToDateInformation();
    final List<Fuel> anreFuelPrice = fuelService.getANREFuelPrice();
    return fuelConverter.toMessage(anreFuelPrice);
  }

  @Override
  public List<String> getAllFuelPriceList() {
    final List<Fuel> allFuelPriceList = fuelService.getAllFuelPriceList();

    final Map<String, List<Fuel>> stationsMap = new HashMap<>();
    getStations(allFuelPriceList).forEach(s -> stationsMap.put(s, new ArrayList<>()));
    allFuelPriceList.forEach(f -> stationsMap.get(f.getStation()).add(f));

    return fuelConverter.toMessage(stationsMap);
  }

  private List<String> getStations(List<Fuel> fuelList) {
    return fuelList.stream()
        .map(Fuel::getStation)
        .distinct()
        .collect(toList());
  }
}
