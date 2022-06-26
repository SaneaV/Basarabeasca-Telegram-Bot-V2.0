package md.basarabeasca.bot.infrastructure.parser.impl;

import static md.basarabeasca.bot.infrastructure.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.dao.mapper.FuelMapper;
import md.basarabeasca.bot.infrastructure.parser.FuelParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FuelParserImpl implements FuelParser {

  private final String site;
  private final FuelMapper fuelMapper;

  public FuelParserImpl(
      @Value("${site.fuel-price.anre}") String site,
      FuelMapper fuelMapper) {
    this.site = site;
    this.fuelMapper = fuelMapper;
  }

  @Override
  public List<Fuel> getANREFuelPrice() throws IOException {
    final InputStream fuelInputStream = new URL(site).openStream();
    final String fuelInJsonString = readFromInputStream(fuelInputStream);
    final JSONObject fuelInJsonObject = new JSONObject(fuelInJsonString);
    return fuelMapper.toEntity(fuelInJsonObject);
  }
}
