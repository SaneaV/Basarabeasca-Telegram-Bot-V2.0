package md.basarabeasca.bot.domain.fuel;

import java.util.List;
import md.basarabeasca.bot.infrastructure.jpa.FuelJpa;
import org.json.JSONObject;

public interface FuelMapper {

  List<Fuel> toEntity(JSONObject jsonObject);

  List<Fuel> toEntity(List<Object> jsonObject);

  Fuel toEntity(FuelJpa fuelJpa);
}
