package md.basarabeasca.bot.dao.mapper.impl;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
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
  private static final String GPL = "gpl";
  private static final String GASOLINE = "gasoline";
  private static final String DIESEL = "Diesel";
  private static final String PETROL = "Petrol";
  private static final String GAS = "Gas";
  private static final String STATION_NAME = "station_name";

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
  public List<Fuel> toEntity(List<Object> jsonObject) {
    final List<Fuel> fuelList = new ArrayList<>();

    jsonObject.stream()
        .map(f -> (HashMap<String, Object>) f)
        .forEach(f -> {
          final String stationName = (String) f.get(STATION_NAME);
          final Fuel petrol = new Fuel(stationName, PETROL, (Double) f.get(GASOLINE));
          final Fuel diesel = new Fuel(stationName, DIESEL, (Double) f.get(DIESEL.toLowerCase()));
          final Fuel gas = new Fuel(stationName, GAS, (Double) f.get(GPL));
          fuelList.addAll(asList(petrol, diesel, gas));
        });

    return fuelList;
  }

  @Override
  public Fuel toEntity(FuelJpa fuelJpa) {
    return Fuel.builder()
        .type(fuelJpa.getType())
        .price(fuelJpa.getPrice())
        .build();
  }
}
