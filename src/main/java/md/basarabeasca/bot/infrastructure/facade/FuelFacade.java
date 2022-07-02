package md.basarabeasca.bot.infrastructure.facade;

import java.util.List;
import java.util.Map;

public interface FuelFacade {

  String getANREFuelPrice();

  List<String> getAllFuelPriceList();

  Map<String, String> getBestFuelPriceFor(String fuelType);
}
