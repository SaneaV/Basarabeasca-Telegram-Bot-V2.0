package md.basarabeasca.bot.dao.mapper;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.dao.repository.model.FuelJpa;
import org.json.JSONObject;

public interface FuelMapper {

  List<Fuel> toEntity(JSONObject jsonObject);

  List<Fuel> toEntity(List<Object> jsonObject);

  Fuel toEntity(FuelJpa fuelJpa);
}
