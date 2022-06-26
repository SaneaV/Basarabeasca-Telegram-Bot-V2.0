package md.basarabeasca.bot.dao.mapper.impl;

import static java.util.Arrays.asList;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.dao.mapper.FuelMapper;
import md.basarabeasca.bot.dao.repository.model.FuelJpa;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class FuelMapperImpl implements FuelMapper {

  private static final String MOTORINA_PRICE = "m_pc";
  private static final String BENZIN_PRICE = "b_pc";
  private static final String DIESEL = "Diesel";
  private static final String PETROL = "Petrol";

  @Override
  public List<Fuel> toEntity(JSONObject jsonObject) {
    if (jsonObject.isEmpty()) {
      return null;
    }

    final Fuel petrol = Fuel.builder()
        .type(PETROL)
        .price(jsonObject.getDouble(BENZIN_PRICE))
        .build();

    final Fuel diesel = Fuel.builder()
        .type(DIESEL)
        .price(jsonObject.getDouble(MOTORINA_PRICE))
        .build();

    return asList(petrol, diesel);
  }

  @Override
  public Fuel toEntity(FuelJpa fuelJpa) {
    return new Fuel(fuelJpa.getType(), fuelJpa.getPrice());
  }
}
