package md.basarabeasca.bot.infrastructure.converter;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;

public interface FuelConverter {

  String toMessage(List<Fuel> fuelList);
}
