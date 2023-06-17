package md.basarabeasca.bot.facade.api;

import java.util.List;
import java.util.Map;

public interface FuelFacade {

  String getANREFuelPrice();

  List<String> getAllFuelPriceList();

  Map<String, String> getBestFuelPriceFor(String fuelType);
}
