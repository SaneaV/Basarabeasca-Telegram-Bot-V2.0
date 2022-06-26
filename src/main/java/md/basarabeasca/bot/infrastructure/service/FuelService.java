package md.basarabeasca.bot.infrastructure.service;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;

public interface FuelService {

  List<Fuel> getANREFuelPrice();

  void updateANREFuelPrice();
}
