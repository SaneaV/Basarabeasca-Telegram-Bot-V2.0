package md.basarabeasca.bot.infrastructure.service.api;

import java.util.List;
import md.basarabeasca.bot.domain.fuel.Fuel;

public interface FuelService {

  List<Fuel> getANREFuelPrice();

  List<Fuel> getAllFuelPriceList();

  List<Fuel> getBestFuelPriceFor(String fuelType);

  void updateANREFuelPrice();
}
