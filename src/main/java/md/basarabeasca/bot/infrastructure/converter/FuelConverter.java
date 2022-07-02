package md.basarabeasca.bot.infrastructure.converter;

import java.util.List;
import java.util.Map;
import md.basarabeasca.bot.dao.domain.Fuel;

public interface FuelConverter {

  String toMessage(List<Fuel> fuelList);

  List<String> toMessage(Map<String, List<Fuel>> fuelList);

  String toMessage(Fuel fuel);
}
