package md.basarabeasca.bot.infrastructure.parser;

import static java.util.stream.Collectors.toList;
import static md.basarabeasca.bot.infrastructure.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import md.basarabeasca.bot.domain.fuel.Fuel;
import md.basarabeasca.bot.domain.fuel.FuelMapper;
import md.basarabeasca.bot.infrastructure.parser.api.FuelParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FuelParserImpl implements FuelParser {

  private static final String BUA = "bua";
  private static final String BASARABEASCA = "Basarabeasca";

  private final String anreMainPriceSite;
  private final String anreFullPriceSite;
  private final FuelMapper fuelMapper;

  public FuelParserImpl(
      @Value("${site.fuel-price.anre}") String anreMainPriceSite,
      @Value("${sit.best-fuel-price.anre}") String anreFullPriceSite,
      FuelMapper fuelMapper) {
    this.anreMainPriceSite = anreMainPriceSite;
    this.anreFullPriceSite = anreFullPriceSite;
    this.fuelMapper = fuelMapper;
  }

  @Override
  public List<Fuel> getANREFuelPrice() {
    try {
      final String jsonString = getJsonString(anreMainPriceSite);
      return fuelMapper.toEntity(new JSONObject(jsonString));
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public List<Fuel> getAllFuelPriceList() {
    try {
      final String jsonString = getJsonString(anreFullPriceSite);
      final JSONArray jsonArray = new JSONArray(jsonString);
      final List<Object> filteredList = jsonArray.toList().stream()
          .map(f -> (HashMap<String, Object>) f)
          .filter(f -> f.get(BUA).equals(BASARABEASCA))
          .collect(toList());
      return fuelMapper.toEntity(filteredList);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  private String getJsonString(String site) throws IOException {
    final InputStream inputStream = new URL(site).openStream();
    return readFromInputStream(inputStream);
  }
}
